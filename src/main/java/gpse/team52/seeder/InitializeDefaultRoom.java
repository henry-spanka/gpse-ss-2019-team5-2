package gpse.team52.seeder;

import gpse.team52.contract.RoomService;
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

    final Room room1 = new Room("mail100", "name", 5, 6, "description");
    final Room room2 = new Room("mail101", "name2", 2, 2, "description2");
}
