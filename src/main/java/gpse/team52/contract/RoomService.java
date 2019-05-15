package gpse.team52.contract;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Location;
import gpse.team52.domain.Room;

    /**
     * Room service.
     */
public interface RoomService {
    Room createRoom(int seats, int expandableSeats, String email, Location location);
    Location createLocation(String name);

    Iterable<Location> getAllLocations();

    Iterable<Room> getAllRooms();
    Optional<Room> getRoom(UUID roomID);
}
