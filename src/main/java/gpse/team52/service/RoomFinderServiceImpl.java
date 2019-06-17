package gpse.team52.service;

import java.time.LocalDateTime;
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

    @Override
    public List<Room> findBest(final Meeting meeting, Map<String, List<Room>> roomsForNew) throws NoRoomAvailableException{
        final List<Room> rooms = new ArrayList<>();
        Set<MeetingRoom> set = meeting.getRooms() ;
/*
        MeetingCreationForm newCreation = new MeetingCreationForm();
        // setting values to create new meeting
        newCreation.setStartTime(meeting.getStartAt().toLocalTime());
        newCreation.setEndTime(meeting.getEndAt().toLocalTime());
        newCreation.setStartDate(meeting.getStartAt().toLocalDate());
        newCreation.setEndDate(meeting.getEndAt().toLocalDate());
        newCreation.setName(meeting.getTitle());

 */
        // get Rooms in which meeting is held, remove this one from available list
        Iterator<MeetingRoom> iterator = set.iterator();
        while (iterator.hasNext()) {
            MeetingRoom meetingRoom = iterator.next();
            Room room = meetingRoom.getRoom();
            if (roomsForNew.containsValue(room)) {
                // TODO test if meeting coul be held in another room
                //  if meeting with the same timespan is held in a room in which the new meeting could fit
            }
        }
        return rooms;
    }

    private void filterUnavailableRooms(final List<Room> rooms, final LocalDateTime start, final LocalDateTime end) {
        final List<UUID> conflicts = meetingRepository.getMeetingRoomMappingInTimeFrameAndFlexibleIsFalse(start, end, false) // if meeting shouldn't be rebookable flexibke = false
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
