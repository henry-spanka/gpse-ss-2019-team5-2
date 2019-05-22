package gpse.team52.seeder;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import gpse.team52.contract.EquipmentService;
import gpse.team52.contract.MeetingService;
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
public class InitializeDefaultMeetings {

    private static final String DEFAULT_PASSWORD = "test";

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
            user1 = userService.createUser(form1, true, "ROLE_USER"); //NOPMD
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
            user2 = userService.createUser(form2, true, "ROLE_USER"); //NOPMD
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
            return;
        }

        Location location1 = roomService.createLocation("Bielefeld");
        Location location2 = roomService.createLocation("Gütersloh");
        Room room1 = roomService.createRoom(12, 2, "bielefeldroom@example.de", location1, "Hörsaal");
        Room room2 = roomService.createRoom(8, 0, "gueterslohroom@example.de", location2, "Konferenzraum");

        Equipment equipment1 = equipmentService.createEquipment("whiteboard");
        Equipment equipment2 = equipmentService.createEquipment("beamer");
        Equipment equipment3 = equipmentService.createEquipment("flipchart");
        room1.addEquipment(equipment1, equipment2, equipment3);
        room2.addEquipment(equipment3);

        roomService.update(room1);
        roomService.update(room2);

        final Meeting meeting1 = new Meeting("Tolles Meeting");
        meeting1.setStartAt(LocalDateTime.of(2019, 5, 10, 10, 15));
        meeting1.setEndAt(LocalDateTime.of(2019, 5, 10, 11, 45));
        meeting1.setDescription("Tolle Beschreibung");
        meeting1.setOwner(user1);
        meeting1.addParticipant(new Participant(user1));

        final Meeting meeting2 = new Meeting("Nicht so tolles Meeting");
        meeting2.setStartAt(LocalDateTime.of(2019, 5, 11, 14, 0));
        meeting2.setEndAt(LocalDateTime.of(2019, 5, 11, 15, 0));
        meeting2.setDescription("Nicht so tolle Beschreibung");
        meeting2.setOwner(user2);
        meeting2.addParticipant(new Participant(user2));

        final Meeting meeting3 = new Meeting("Geheimes Meeting");
        meeting3.setStartAt(LocalDateTime.of(2019, 5, 12, 23, 0));
        meeting3.setEndAt(LocalDateTime.of(2019, 5, 12, 23, 30));
        meeting3.setOwner(user1);
        meeting3.addParticipant(new Participant(user1));
        meeting3.addParticipant(new Participant("externerkunde@example.de",
        "Günther", "Schmidt"));

        meetingService.createMeeting(meeting1, room1, 23);

        meetingService.createMeeting(meeting2, room2, 18);

        meetingService.createMeeting(meeting3, room1, 3);
    }
}
