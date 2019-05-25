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

/**
 * Initializes Default Meetings in the database.
 */
@Service
public class InitializeDefaultRooms {

    private static final String DEFAULT_PASSWORD = "rooms";
    private final UserService userService;
    private final RoomService roomService;
    private final EquipmentService equipmentService;

    /**
     * Constructor for the used services.
     * @param userService Service for user
     * @param roomService Service for rooms
     * @param equipmentService Service for equipment
     */
    @Autowired
    public InitializeDefaultRooms(final UserService userService,
    final RoomService roomService, final EquipmentService equipmentService) {
        this.userService = userService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
    }

    /**
     * Initializes users to test search for rooms.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {
        final UserRegistrationForm form1 = new UserRegistrationForm();
        form1.setFirstName("Pia");
        form1.setLastName("Hippel");
        form1.setEmail("phippel@example.org");
        form1.setUsername("phippel");
        form1.setPassword(DEFAULT_PASSWORD);
        form1.setPasswordConfirm(DEFAULT_PASSWORD);

        User user;

        try {
            user = userService.createUser(form1, true, "ROLE_USER"); //NOPMD
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            return;
        }

        Location bielefeld = roomService.getLocation("Bielefeld").orElseThrow();
        Location guetersloh = roomService.getLocation("Gütersloh").orElseThrow();
        Location duesseldorf = roomService.getLocation("Düsseldorf").orElseThrow();

        Room roomA = roomService.createRoom(12, 2, "bf@example.de", bielefeld, "Bielefeld12",
        "layoutBlue");
        Room roomB = roomService.createRoom(8, 0, "gt@example.de", guetersloh, "GüterslohRoom",
        "layoutRed");
        Room roomC = roomService.createRoom(20, 0, "dd@example.org", duesseldorf, "DüsseldorfRoom",
        "layoutBlue");
        Room roomD = roomService.createRoom(1, 2, "bf1@example.de", bielefeld, "Bielefeld1",
        "layoutBlue");
        Room roomE = roomService.createRoom(20, 2, "bf20@example.de", bielefeld, "Bielefeld20",
        "layoutBlue");
        //TODO add equipment

        roomService.update(roomA);
        roomService.update(roomB);
        roomService.update(roomC);
    }
}
