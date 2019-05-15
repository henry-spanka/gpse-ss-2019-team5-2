package gpse.team52.seeder;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes the Default Room in the database.
 */
@Service
public class InitializeDefaultRoom {

    private RoomService roomService;

    @Autowired
    public InitializeDefaultRoom() {
        this.roomService = roomService;
    }

    final Room room1 = new Room(3, 0, "mail100", new Location("Bielefeld"));
    final Room room2 = new Room(4, 2, "mail101", new Location("Düsseldorf"));
}
