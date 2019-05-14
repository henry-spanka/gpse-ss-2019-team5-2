package gpse.team52.contract;

import gpse.team52.domain.Location;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;

import java.time.LocalDateTime;

public interface RoomService {
    Room createRoom(String email, String name, int seats, int extraSeats, String location);
    Location createLocation(String name);
    Iterable<Room> getAllRooms();
}
