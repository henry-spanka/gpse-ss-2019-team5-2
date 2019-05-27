package gpse.team52.seeder;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes Default Meetings in the database.
 */
@Service
public class InitializeDefaultMeetings {

    private static final String DEFAULT_PASSWORD = "test";

    private static final String DEFAULT_USER_ROLE = "ROLE_USER";

    private final MeetingService meetingService;
    private final UserService userService;
    private final RoomService roomService;
    private final EquipmentService equipmentService;

    /**
     * Constructor for the used services.
     * @param meetingService Service for meetings
     * @param userService Service for user
     * @param roomService Service for rooms
     * @param equipmentService Service for equipment
     */
    @Autowired
    public InitializeDefaultMeetings(
    final MeetingService meetingService, final UserService userService,
    final RoomService roomService, final EquipmentService equipmentService) {
        this.meetingService = meetingService;
        this.userService = userService;
        this.roomService = roomService;
        this.equipmentService = equipmentService;
    }

    /**
     * Initializes users for testing meeting features.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {
        final UserRegistrationForm form1 = new UserRegistrationForm();
        form1.setFirstName("Julius");
        form1.setLastName("Ellermann");
        form1.setEmail("jellermann@example.org");
        form1.setUsername("jellermann");
        form1.setPassword(DEFAULT_PASSWORD);
        form1.setPasswordConfirm(DEFAULT_PASSWORD);

        User user1;

        try {
            user1 = userService.createUser(form1, true, DEFAULT_USER_ROLE);
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
            return;
        }

        final UserRegistrationForm form2 = new UserRegistrationForm();
        form2.setFirstName("Lukas");
        form2.setLastName("Dyballa");
        form2.setEmail("ldyballan@example.org");
        form2.setUsername("ldyballa");
        form2.setPassword(DEFAULT_PASSWORD);
        form2.setPasswordConfirm(DEFAULT_PASSWORD);

        User user2;

        try {
            user2 = userService.createUser(form2, true, DEFAULT_USER_ROLE);
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
            return;
        }

        final Location location1 = roomService.getLocation("Bielefeld").orElseThrow();
        final Location location2 = roomService.getLocation("Gütersloh").orElseThrow();

        final Room room1 = roomService.createRoom(12, 2, "bielefeldroom@example.de", location1, "BielefeldRoom",
        "layoutBlue");
        final Room room2 = roomService.createRoom(8, 0, "guetersloh@example.de", location2, "GüterslohRoom",
        "layoutRed");

        final Equipment equipment1 = equipmentService.createEquipment("whiteboard");
        final Equipment equipment2 = equipmentService.createEquipment("beamer");
        final Equipment equipment3 = equipmentService.createEquipment("flipchart");
        room1.addEquipment(equipment1, equipment2, equipment3);
        room2.addEquipment(equipment3);

        roomService.update(room1);
        roomService.update(room2);

        final Meeting meeting1 = new Meeting("Daily Scum");
        meeting1.setStartAt(LocalDateTime.of(2019, 5, 24, 10, 15));
        meeting1.setEndAt(LocalDateTime.of(2019, 5, 24, 11, 45));
        meeting1.setDescription("Scrum XYZ");
        meeting1.setOwner(user1);
        meeting1.addParticipant(new Participant(user1));

        final Meeting meeting2 = new Meeting("Budget Meeting");
        meeting2.setStartAt(LocalDateTime.of(2019, 5, 25, 14, 0));
        meeting2.setEndAt(LocalDateTime.of(2019, 5, 25, 15, 0));
        meeting2.setDescription("Budget evaluation with coe");
        meeting2.setOwner(user2);
        meeting2.addParticipant(new Participant(user2));

        final Meeting meeting3 = new Meeting("Weekly Review");
        meeting3.setStartAt(LocalDateTime.of(2019, 5, 26, 23, 0));
        meeting3.setEndAt(LocalDateTime.of(2019, 5, 26, 23, 30));
        meeting3.setOwner(user1);
        meeting3.addParticipant(new Participant(user1));
        meeting3.addParticipant(new Participant("externerkunde@example.de",
        "Günther", "Schmidt"));

        meetingService.createMeeting(meeting1, room1, 23);

        meetingService.createMeeting(meeting2, room2, 18);

        meetingService.createMeeting(meeting3, room1, 3);

    }
}
