package gpse.team52.service;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import gpse.team52.repository.LocationRepository;
import gpse.team52.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Room createRoom(final int seats, final int expandableSeats,
                           final String email, final Location location) {
        final Room room = new Room(seats, expandableSeats, email, location);

        return roomRepository.save(room);
    }

    @Override
    public Location createLocation(final String name) {
        final Location location = new Location(name);

        return locationRepository.save(location);
    }
}
