package gpse.team52.contract;

import gpse.team52.domain.Location;
import gpse.team52.domain.Room;

public interface RoomService {

    Room createRoom(int seats, int expandableSeats, String email, Location location);

    Location createLocation(String name);
}
