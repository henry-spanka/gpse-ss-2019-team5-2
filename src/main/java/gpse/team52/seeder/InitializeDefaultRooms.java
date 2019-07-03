package gpse.team52.seeder;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import gpse.team52.contract.*;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes Default Meetings in the database.
 */
@Service
public class InitializeDefaultRooms {

    private final UserService userService;
    private final RoomService roomService;
    private final EquipmentService equipmentService;
    private final LocationService locationService;
    private final MeetingService meetingService;

    private final InitializeDefaultRoles initializeDefaultRoles;

    /**
     * Required to make it dependent on InitializeDefaultMeetings.
     */
    private final InitializeDefaultMeetings initializeDefaultMeetings;

    private final InitializeDefaultUser initializeDefaultUser;

    /**
     * Constructor for the used services.
     *
     * @param userService
     * @param roomService               Service for roomsrvice          Equpiment Service.
     * @param equipmentService
     * @param locationService           Location Service.
     * @param roleService               Role Service.
     * @param initializeDefaultRoles    InitializeDefaultRoles.
     * @param initializeDefaultMeetings InitializeDefaultMeetings.
     * @param meetingService            MeetingService
     * @param initializeDefaultUser     InitializeDefaultUser.
     */
    @Autowired
    public InitializeDefaultRooms(final UserService userService,
                                  final RoomService roomService, final EquipmentService equipmentService,
                                  final LocationService locationService,
                                  final RoleService roleService, final InitializeDefaultRoles initializeDefaultRoles,
                                  final InitializeDefaultMeetings initializeDefaultMeetings,
                                  final MeetingService meetingService,
                                  final InitializeDefaultUser initializeDefaultUser) {
        this.userService = userService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
        this.locationService = locationService;
        this.initializeDefaultRoles = initializeDefaultRoles;
        this.initializeDefaultMeetings = initializeDefaultMeetings;
        this.meetingService = meetingService;
        this.initializeDefaultUser = initializeDefaultUser;

        roleService.getByName("ROLE_USER").orElseThrow();
    }

    /**
     * Initializes rooms to test search for rooms.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {

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
        final Room rt = roomService.createRoom(60, 10, "ratingen@example.de", ratingen, "Ratingen", "layoutBlue");
        final Room rt2 = roomService.createRoom(40, 5, "ratingen2@example.de", ratingen, "Ratingen2", "layoutBlue");
        final Room rt3 = roomService.createRoom(100, 20, "ratingen3@example.de", ratingen, "Ratingen3", "layoutBlue");
        final Room rt4 = roomService.createRoom(150, 10, "ratingen4@example.de", ratingen, "Ratingen4", "layoutRed");
        final Room rt5 = roomService.createRoom(100, 10, "notcomfy@example.de", ratingen, "Not so comfy room", "layoutBlue");
        final Room mb = roomService.createRoom(10, 1, "mumbai@example.de", mumbai, "Mumbai", "layoutRed");
        final Room mb1 = roomService.createRoom(14, 2, "mumbai2@example.de", mumbai, "Mumbai2", "layoutBlue");
        final Room mb2 = roomService.createRoom(4, 1, "mumbai3@example.de", mumbai, "Mumbai3", "layoutRed");

        final Equipment beamer = equipmentService.getEquipment("Beamer").orElseThrow();
        final Equipment telco = equipmentService.createEquipment("Phone System");
        final Equipment speakers = equipmentService.createEquipment("Speakers");
        final Equipment whiteboard = equipmentService.getEquipment("Whiteboard").orElseThrow();
        rt.addEquipment(telco, beamer, whiteboard);
        rt2.addEquipment(telco, beamer);

        rt3.addEquipment(telco, beamer, speakers);
        rt4.addEquipment(telco, beamer, speakers);
        rt5.addEquipment(telco, beamer, speakers);

        mb.addEquipment(telco);
        mb1.addEquipment(telco);
        mb2.addEquipment(telco);
        roomService.update(rt);
        roomService.update(rt2);
        roomService.update(mb);
        roomService.update(rt3);
        roomService.update(rt4);
        roomService.update(rt5);
        roomService.update(mb1);
        roomService.update(mb2);

        //TODO add equipment

        roomService.update(roomA);
        roomService.update(roomB);
        roomService.update(roomC);

        final User mborat = userService.loadUserByUsername("mborat");
        final User markusc = userService.loadUserByUsername("markusc");

        meetingService.createMeeting("Revenue Report", 137,
        LocalDateTime.of(2019, 9, 27, 9, 0),
        LocalDateTime.of(2019, 9, 27, 14, 0),
        mborat,
        rt4
        );

        meetingService.createMeeting("Sustainability Report", 119,
        LocalDateTime.of(2019, 9, 27, 9, 0),
        LocalDateTime.of(2019, 9, 27, 12, 30),
        markusc,
        rt3
        );
    }
}
