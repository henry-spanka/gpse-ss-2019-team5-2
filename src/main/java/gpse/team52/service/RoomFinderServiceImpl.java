package gpse.team52.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import gpse.team52.contract.RoomFinderService;
import gpse.team52.domain.*;
import gpse.team52.exception.NoRoomAvailableException;
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
     * @param meeting The MeetingCreationForm.
     * @return Map of rooms for each location.
     */
    public Map<String, List<Room>> find(final MeetingCreationForm meeting) {
        final List<String> locations = meeting.getLocations();
        final Map<String, List<String>> equipment = meeting.getEquipment();

        final Map<String, List<Room>> result = new HashMap<>();

        for (final String location : locations) {
            final UUID locationUuid = UUID.fromString(location);

            final List<UUID> equipmentList = Optional.ofNullable(equipment.get(location))
            .orElse(new ArrayList<>()) //NOPMD
            .stream().map(UUID::fromString).collect(Collectors.toList());

            final List<Room> rooms = findMatchingRooms(locationUuid, meeting.getParticipant(location), equipmentList);
            filterUnavailableRooms(rooms, meeting.getStartDateTime(), meeting.getEndDateTime());

            result.put(location, rooms);
        }

        return result;
    }

    @Override
    public List<Room> findBest(final MeetingCreationForm meeting) throws NoRoomAvailableException {
        final List<Room> rooms = new ArrayList<>();

        final Map<String, List<Room>> availableRooms = find(meeting);

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
     *
     * @param meeting The meeting which might be rebooked / moved to another room.
     * @param roomsForNew The rooms which might be possible rooms for the new meeting.
     * @return List of rooms which might be used for the meeting which will be rebooked.
     * @throws NoRoomAvailableException Thrown if no rebooking possible
     */
    @Override
    public List<Room> findOther(final Meeting meeting, Map<String, List<Room>> roomsForNew) throws NoRoomAvailableException{
        final List<Room> rooms = new ArrayList<>();
        Set<MeetingRoom> set = meeting.getRooms() ;
        long timeDif = Duration.between(ZonedDateTime.now(), meeting.getStartAt()).toHours(); //LocalDateTime.now()
        // if meeting is within next 24h there's no rebooking possible
        if (timeDif < 24){
            throw new NoRoomAvailableException("No room available.");
        }
        // currentWhatever = the meeting, room and meetingRoom from meeting which might be moved to another room
        Iterator<MeetingRoom> iterator = set.iterator();
        while (iterator.hasNext()) {
            MeetingRoom currentMeetingRoom = iterator.next();
            Room currentRoom = currentMeetingRoom.getRoom();
            // searching for currentRoom in list of rooms for new meeting
            // only search for other rooms if meeting is held in a rooms which could be used for new meeting
            UUID currentLocationId = currentRoom.getLocation().getLocationId();
            List<Room> roomsAtLoc = roomsForNew.get(currentLocationId.toString());
            if (roomsAtLoc.contains(currentRoom)) {
                // find rooms similar to findMatchingRooms method
                for (final Room room : roomRepository.findByLocationAndSeatsGreaterThanEqual(currentLocationId, currentMeetingRoom.getParticipants())) {
                    final List<UUID> equipmentList = room.getEquipment().stream().map(Equipment::getEquipmentID)
                    .collect(Collectors.toList());
                    if (equipmentList.containsAll(currentRoom.getEquipment())) {
                        // Problem: der raum in dem das meeting stattfindet, kann ja mehr equipment als noetig enthalten, aber meeting selbst hat kein equipment
                        // man sucht dann also moeglicherweise nach mehr equipment als eigentlich gebraucht
                        rooms.add(room);
                    }
                }
                rooms.remove(currentRoom); // remove room in which meeting is currently held from list
                filterUnavailableRoomsWithoutCascade(rooms, meeting.getStartAt(), meeting.getEndAt());
                // right now cascading is not allowed anymore
            }
        }
        return rooms;
    }

    private void filterUnavailableRooms(final List<Room> rooms, final LocalDateTime start, final LocalDateTime end) {
        final List<UUID> conflicts = meetingRepository.getMeetingRoomMappingInTimeFrameAndFlexibleIsFalse(start, end, false) // if meeting shouldn't be rebookable flexible = false
        .stream().map(r -> r.getRoom().getRoomID()).collect(Collectors.toList());

        rooms.removeIf(r -> conflicts.contains(r.getRoomID()));
    }

    private void filterUnavailableRoomsWithoutCascade(final List<Room> rooms, final LocalDateTime start, final LocalDateTime end) {
        final List<UUID> conflicts = meetingRepository.getMeetingRoomMappingInTimeFrame(start, end)
        .stream().map(r -> r.getRoom().getRoomID()).collect(Collectors.toList());

        rooms.removeIf(r -> conflicts.contains(r.getRoomID()));
    }

    private List<Room> findMatchingRooms(final UUID location, final int seats, final List<UUID> equipment) {
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
