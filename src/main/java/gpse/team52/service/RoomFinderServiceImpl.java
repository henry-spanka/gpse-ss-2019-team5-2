package gpse.team52.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import gpse.team52.contract.RoomFinderService;
import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Room;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.repository.MeetingRepository;
import gpse.team52.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomFinderServiceImpl implements RoomFinderService {
    @Autowired
    private final RoomService roomService;

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final MeetingRepository meetingRepository;

    public HashMap<String, List<Room>> find(MeetingCreationForm meeting) {
        List<String> locations = meeting.getLocations();
        Map<String, List<String>> equipment = meeting.getEquipment();

        HashMap<String, List<Room>> result = new HashMap<>();

        for (String location : locations) {
            UUID locationUuid = UUID.fromString(location);

            List<UUID> equipmentList = Optional.ofNullable(equipment.get(location)).orElse(new ArrayList<>())
            .stream().map(UUID::fromString).collect(Collectors.toList());

            List<Room> rooms = findMatchingRooms(locationUuid, meeting.getParticipant(location), equipmentList);
            filterUnavailableRooms(rooms, meeting.getStartDateTime(), meeting.getEndDateTime());

            result.put(location, rooms);
        }

        return result;
    }

    @Override
    public List<Room> findBest(MeetingCreationForm meeting) throws NoRoomAvailableException {
        List<Room> rooms = new ArrayList<>();

        HashMap<String, List<Room>> availableRooms = find(meeting);

        try {
            for (String location : meeting.getLocations()) {
                rooms.add(availableRooms.get(location).iterator().next());
            }
        } catch (NoSuchElementException e) {
            throw new NoRoomAvailableException("No room available.");
        }

        return rooms;
    }

    private void filterUnavailableRooms(List<Room> rooms, LocalDateTime start, LocalDateTime end) {
        List<UUID> conflicts = meetingRepository.getMeetingRoomMappingInTimeFrame(start, end)
        .stream().map(r -> r.getRoom().getRoomID()).collect(Collectors.toList());

        rooms.removeIf(r -> conflicts.contains(r.getRoomID()));
    }

    private List<Room> findMatchingRooms(UUID location, int seats, List<UUID> equipment) {
        List<Room> rooms = new ArrayList<>();

        for (Room room : roomRepository.findByLocationAndSeatsGreaterThanEqual(location, seats)) {
            List<UUID> equipmentList = room.getEquipment().stream().map(Equipment::getEquipmentID).collect(Collectors.toList());

            if (equipmentList.containsAll(equipment)) {
                rooms.add(room);
            }
        }

        return rooms;
    }
}
