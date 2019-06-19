package gpse.team52.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import gpse.team52.repository.EquipmentRepository;
import gpse.team52.repository.LocationRepository;
import gpse.team52.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Room service.
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomRepository roomRepository;

    @Autowired
    private final LocationRepository locationRepository;

    @Autowired
    private final EquipmentRepository equipmentRepository;

    @Override
    public Room createRoom(final int seats, final int expandableSeats, final String email, final Location location,
                           final String roomName, final String layoutName) {
        final Room room = new Room(seats, expandableSeats, email, location, roomName, layoutName);
        return roomRepository.save(room);
    }

    public Room update(final Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Iterable<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Optional<Room> getRoom(final UUID roomID) {
        return roomRepository.findById(roomID);
    }

    @Override
    public Iterable<Room> getAvailableRooms(final Location location, final int seats, final Date date, final int start,
                                            final int end, final List<Equipment> equipment) {
        return roomRepository.findByLocationAndSeatsGreaterThanEqual(location, seats);
    }

    @Override
    public List<Location> findByLocationId(final List<UUID> uuidList) {
        return locationRepository.findAllByLocationIdIn(uuidList);
    }

    @Override
    public List<Location> findByLocationIdFromString(final List<String> uuidList) {
        return findByLocationId(uuidList.stream().map(UUID::fromString).collect(Collectors.toList()));
    }
}
