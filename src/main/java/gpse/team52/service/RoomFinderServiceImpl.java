package gpse.team52.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import gpse.team52.contract.RoomFinderService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.MeetingRoom;
import gpse.team52.domain.Room;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.exception.RebookingImpossibleException;
import gpse.team52.exception.RebookingNotNecessaryException;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.repository.MeetingRepository;
import gpse.team52.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the room finder interface.
 */
@Service
@RequiredArgsConstructor
public class RoomFinderServiceImpl implements RoomFinderService {
    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final MeetingRepository meetingRepository;

    /**
     * Find all matching rooms for the MeetingCreationForm request.
     *
     * @param meeting The MeetingCreationForm.
     * @return Map of rooms for each location.
     */
    public Map<String, List<Room>> find(final MeetingCreationForm meeting) { //NOPMD
        final List<String> locations = meeting.getLocations();
        final Map<String, List<String>> equipment = meeting.getEquipment(); //NOPMD

        final Map<String, List<Room>> result = new HashMap<>();

        for (final String location : locations) {
            final UUID locationUuid = UUID.fromString(location);

            final List<UUID> equipmentList = Optional.ofNullable(equipment.get(location))
            .orElse(new ArrayList<>()) //NOPMD
            .stream().map(UUID::fromString).collect(Collectors.toList());

            final List<Room> rooms = findMatchingRooms(locationUuid, meeting.getParticipant(location), equipmentList);
            filterUnavailableRoomsWithFlexible(rooms, meeting.getStartDateTime(), meeting.getEndDateTime());

            result.put(location, rooms);
        }

        return result;
    }

    @Override
    public List<Room> findBest(final MeetingCreationForm meeting) throws NoRoomAvailableException { //NOPMD
        final List<Room> rooms = new ArrayList<>(); //NOPMD

        final Map<String, List<Room>> availableRooms = find(meeting); //NOPMD

        try {
            for (final String location : meeting.getLocations()) {
                rooms.add(availableRooms.get(location).iterator().next());
            }
        } catch (NoSuchElementException e) {
            throw new NoRoomAvailableException("No room available.", e);
        }

        return rooms;
    }

    /**
     * @param meeting     The meeting which might be rebooked / moved to another room.
     * @param roomsForNew The rooms which might be possible rooms for the new meeting.
     * @return A map of the used room with its alternatives
     * @throws RebookingImpossibleException   If time limit exceeded or list for every room is empty
     * @throws RebookingNotNecessaryException If no rooms of the meeting interfere with the new meeting
     */
    @Override
    public Map<String, List<Room>> findOther(final Meeting meeting, final Map<String, List<Room>> roomsForNew) //NOPMD
    throws RebookingImpossibleException, RebookingNotNecessaryException {

        final long timeDif = Duration.between(LocalDateTime.now(), meeting.getStartAt()).toHours(); //LocalDateTime.now()
        // if meeting is within next 24h there's no rebooking possible
        if (timeDif < 24) { //NOPMD
            throw new RebookingImpossibleException("Time limit exceeded.");
        }

        List<Room> rooms;
        final Set<MeetingRoom> setForOld = meeting.getRooms();

        // RoomID of room which is currently used and its alternatives
        final Map<String, List<Room>> alternatives = new HashMap<>();

        // currentWhatever = the meeting, room and meetingRoom from meeting which might be moved to another room
        final Iterator<MeetingRoom> itOld = setForOld.iterator();
        while (itOld.hasNext()) {
            boolean roomMightBeUsed = false; //NOPMD
            rooms = new ArrayList<>(); //NOPMD // reset alternative room list
            final MeetingRoom currentMeetingRoom = itOld.next();
            final Room currentRoom = currentMeetingRoom.getRoom();
            // searching for currentRoom in list of rooms for new meeting
            // only search for other rooms if meeting is held in a room which could be used for new meeting
            final UUID currentLocationId = currentRoom.getLocation().getLocationId();
            final List<Room> roomsAtLoc = roomsForNew.get(currentLocationId.toString());
            if (roomsAtLoc != null) {
                for (final Room roomAtLoc : roomsAtLoc) {
                    // check if room is included in roomsForNew
                    if (roomAtLoc.getRoomID().equals(currentRoom.getRoomID())) {
                        // find rooms similar to findMatchingRooms method
                        roomMightBeUsed = true;
                        for (final Room room : roomRepository.findByLocationAndSeatsGreaterThanEqual(
                        currentLocationId, currentMeetingRoom.getParticipants())) {
                            final List<UUID> equipmentList = room.getEquipment().stream().map(Equipment::getEquipmentID)
                            .collect(Collectors.toList());
                            final List<UUID> currentEq = currentRoom.getEquipment().stream().map(Equipment::getEquipmentID)
                            .collect(Collectors.toList());
                            if (equipmentList.containsAll(currentEq)) { //NOPMD
                                // Problem: der raum in dem das meeting stattfindet, kann ja mehr equipment als
                                // noetig enthalten, aber meeting selbst hat kein equipment
                                // man sucht dann also moeglicherweise nach mehr equipment als eigentlich gebraucht
                                rooms.add(room);
                            }
                        }

                        // remove room in which meeting is currently held from list
                        rooms.removeIf((Room room) -> room.getRoomID().equals(currentRoom.getRoomID()));
                        filterUnavailableRooms(rooms, meeting.getStartAt(), meeting.getEndAt());
                        // right now cascading is not allowed anymore
                        break; // bc room won't be in the list twice, so no need to compare any more rooms
                    }
                }
                // add room(s) according to location to the hashmap, bc rooms are not sorted by location in meetingRoom
                // only add if it is included in rooms for new meeting
                if (roomMightBeUsed) {
                    if (alternatives.containsKey(currentRoom.getRoomID().toString())) {
                        alternatives.get(currentRoom.getRoomID().toString()).addAll(rooms);
                    } else {
                        alternatives.put(currentRoom.getRoomID().toString(), rooms);
                    }
                }
            }
        }
        if (alternatives.isEmpty()) {
            throw new RebookingNotNecessaryException("Rooms do not interfere.");
        }
        return alternatives;
    }

    private void filterUnavailableRoomsWithFlexible(final List<Room> rooms, final LocalDateTime start,
                                                    final LocalDateTime end) {
        final List<UUID> conflicts = meetingRepository //NOPMD
        .getMeetingRoomMappingInTimeFrameAndDisableRebookMeetingIsTrue(start, end, true)
        // if meeting shouldn't be rebookable disableRebooking = true
        .stream().map(r -> r.getRoom().getRoomID()).collect(Collectors.toList());

        rooms.removeIf(r -> conflicts.contains(r.getRoomID()));
    }

    private void filterUnavailableRooms(final List<Room> rooms, final LocalDateTime start, final LocalDateTime end) {
        final List<UUID> conflicts = meetingRepository.getMeetingRoomMappingInTimeFrame(start, end) //NOPMD
        .stream().map(r -> r.getRoom().getRoomID()).collect(Collectors.toList());

        rooms.removeIf(r -> conflicts.contains(r.getRoomID()));
    }

    private List<Room> findMatchingRooms(final UUID location, final int seats, final List<UUID> equipment) { //NOPMD
        final List<Room> rooms = new ArrayList<>();

        for (final Room room : roomRepository.findByLocationAndSeatsGreaterThanEqual(location, seats)) {
            final List<UUID> equipmentList = room.getEquipment().stream().map(Equipment::getEquipmentID)
            .collect(Collectors.toList());

            if (equipmentList.containsAll(equipment)) {
                rooms.add(room);
            }
        }

        return rooms;
    }

}
