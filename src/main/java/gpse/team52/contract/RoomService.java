package gpse.team52.contract;

import gpse.team52.domain.Location;
import gpse.team52.domain.Room;

import java.util.Optional;
import java.util.UUID;

public interface RoomService {
    // TODO check here
    Room update(Room room);
    Room createRoom(int seats, int expandableSeats, String roomName, String email, Location location);
    Location createLocation(String name);

    Iterable<Location> getAllLocations();

    Iterable<Room> getAllRooms();
    Optional<Room> getRoom(UUID roomID);
}
