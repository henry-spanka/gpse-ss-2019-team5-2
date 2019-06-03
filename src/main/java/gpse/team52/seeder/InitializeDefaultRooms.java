package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.LocationService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
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
    private final LocationService locationService;

    /**
     * Required to make it dependent on InitializeDefaultMeetings.
     */
    private final InitializeDefaultMeetings initializeDefaultMeetings;

    /**
     * Constructor for the used services.
     *
     * @param userService Service for user
     * @param roomService Service for rooms
     */
    @Autowired
    public InitializeDefaultRooms(final UserService userService,
                                  final RoomService roomService, final EquipmentService equipmentService, final LocationService locationService,
                                  final InitializeDefaultMeetings initializeDefaultMeetings) {
        this.userService = userService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
        this.locationService = locationService;
        this.initializeDefaultMeetings = initializeDefaultMeetings;
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

        try {
            userService.createUser(form1, true, "ROLE_USER"); //NOPMD
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            return;
        }

        final Location bielefeld = locationService.getLocation("Bielefeld").orElseThrow();
        final Location guetersloh = locationService.getLocation("Gütersloh").orElseThrow();
        final Location duesseldorf = locationService.getLocation("Düsseldorf").orElseThrow();

        final Room roomA = roomService.createRoom(12, 2, "bf@example.de", bielefeld, "Bielefeld12",
        "layoutBlue"); //NOPMD
        final Room roomB = roomService.createRoom(8, 0, "gt@example.de", guetersloh, "GüterslohRoom",
        "layoutRed");
        final Room roomC = roomService.createRoom(20, 0, "dd@example.org", duesseldorf, "DüsseldorfRoom",
        "layoutBlue");
        roomService.createRoom(1, 2, "bf1@example.de", bielefeld, "Bielefeld1",
        "layoutBlue");
        roomService.createRoom(20, 2, "bf20@example.de", bielefeld, "Bielefeld20",
        "layoutBlue");

        // ------------------------ Use Case -----------------
        final Location ratingen = locationService.getLocation("Ratingen").orElseThrow();
        final Location mumbai = locationService.getLocation("Mumbai").orElseThrow();
        Room rt = roomService.createRoom(60, 10, "ratingen@example.de", ratingen, "Ratingen", "layoutBlue");
        Room rt2 = roomService.createRoom(40, 5, "ratingen2@example.de", ratingen, "Ratingen2", "layoutBlue");
        Room rt3 = roomService.createRoom(100, 20, "ratingen3@example.de", ratingen, "Ratingen3", "layoutBlue");
        Room mb = roomService.createRoom(10, 0, "mumbai@example.de", mumbai, "Mumbai", "layoutRed");
        Room mb2 = roomService.createRoom(50, 10, "mumbai2@example.de", mumbai, "Mumbai2", "layoutRed");
        Room mb3 = roomService.createRoom(22, 23, "mumbai3@example.de", mumbai, "Mumbai3", "layoutRed");
        Room mb4 = roomService.createRoom(14, 0, "mumbai4@example.de", mumbai, "Mumbai4", "layoutRed");

        final Equipment projektor = equipmentService.createEquipment("Projektor");
        final Equipment telco = equipmentService.createEquipment("Telefonanlage");
        Equipment beamer = equipmentService.getEquipment("Beamer").orElseThrow();
        Equipment whiteboard = equipmentService.getEquipment("Whiteboard").orElseThrow();
        rt.addEquipment(telco, projektor, beamer, whiteboard);
        rt2.addEquipment(telco, projektor, beamer);
        rt3.addEquipment(telco, projektor, beamer);
        mb.addEquipment(telco);
        mb2.addEquipment(telco, projektor);
        mb3.addEquipment(telco, beamer, projektor);
        mb4.addEquipment(telco, whiteboard, beamer);
        roomService.update(rt);
        roomService.update(rt2);
        roomService.update(mb);
        roomService.update(rt3);
        roomService.update(mb2);
        roomService.update(mb3);
        roomService.update(mb4);

        //TODO add equipment

        roomService.update(roomA);
        roomService.update(roomB);
        roomService.update(roomC);
    }
}
