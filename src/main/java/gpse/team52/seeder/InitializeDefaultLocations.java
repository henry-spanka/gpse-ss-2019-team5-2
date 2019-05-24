package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.*;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitializeDefaultLocations {

    private static final String DEFAULT_PASSWORD = "rooms";
    private final UserService userService;
    private final RoomService roomService;
    private final EquipmentService equipmentService;

    @Autowired
    public InitializeDefaultLocations(final UserService userService,
                                  final RoomService roomService, final EquipmentService equipmentService) {
        this.userService = userService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
    }

    /**
     * Initializes locations to use them in DefaultMeetings and DefaultRooms.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {

        Location bielefeld = roomService.createLocation("Bielefeld");
        Location guetersloh = roomService.createLocation("Gütersloh");
        Location duesseldorf = roomService.createLocation("Düsseldorf");

    }
}
