package gpse.team52.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import gpse.team52.repository.LocationRepository;
import gpse.team52.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Room service.
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public RoomServiceImpl(final RoomRepository roomRepository,
                           final LocationRepository locationRepository) {
        this.roomRepository = roomRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Room createRoom(final int seats, final int expandableSeats, final String email, final Location location, final String roomName,
                           final String layoutName) {
        final Room room = new Room(seats, expandableSeats, email, location, roomName,layoutName);
//TODO hier das auch so übernehmen mit dem Raum Konstruktor, und im RoomService auch
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        return roomRepository.save(room);
    }


    // TODO ? extra service for location ?
    @Override
    public Location createLocation(final String name) {
        final Location location = new Location(name);
        return locationRepository.save(location);
    }
    @Override
    public Iterable<Location> getAllLocations() { return locationRepository.findAll(); }

    @Override
    public Optional<Location> getLocation(String name){
        return  locationRepository.findByName(name);
    }

    @Override
    public Iterable<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> getRoom(UUID roomID) {
        return roomRepository.findById(roomID);
    }

    @Override
    public Iterable<Room> getAvailableRooms(Location location, int seats, Date date, int start, int end, List<Equipment> equipment) {
        Iterable<Room> availableRooms = roomRepository.findByLocationAndSeatsGreaterThanEqual(location, seats);

        return availableRooms; // TODO implement method to find all available rooms for a meeting
    }
}
