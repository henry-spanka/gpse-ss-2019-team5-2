package gpse.team52.contract;
import gpse.team52.domain.Location;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;

import java.util.Optional;
import java.util.UUID;
    /**
     * Room service.
     */
public interface RoomService {

    Room createRoom(int seats, int expandableSeats, String email, Location location, String roomName, String layoutName);

    Room update(Room room);

    //TODO location in roomService interface??
    Location createLocation(String name);
    Iterable<Location> getAllLocations();

    Iterable<Room> getAllRooms();
    Optional<Room> getRoom(UUID roomID);

    // method to get rooms suitable for meeting
    Iterable<Room> getAvailableRooms(Meeting meeting);
}
