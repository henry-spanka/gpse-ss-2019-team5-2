package gpse.team52.seeder;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes the Default User in the database.
 */
@Service
public class InitializeDefaultMeetings {

    private static final String DEFAULT_PASSWORD = "test";
    private final MeetingService meetingService;

    private final UserService userService;

    @Autowired
    public InitializeDefaultMeetings(final MeetingService meetingService, final UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }

    /**
     * Initializes the default admin user if it doesn't exist already.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @PostConstruct
    public void init() {
        final UserRegistrationForm form1 = new UserRegistrationForm();
        form1.setFirstName("Julius");
        form1.setLastName("Ellermann");
        form1.setEmail("jellermann@example.org");
        form1.setUsername("julius");
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
        form2.setUsername("lukas");
        form2.setPassword(DEFAULT_PASSWORD);
        form2.setPasswordConfirm(DEFAULT_PASSWORD);

        User user2;

        try {
            user2 = userService.createUser(form2, true, "ROLE_USER"); //NOPMD
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
            return;
        }

        final Meeting meeting1 = new Meeting("Tolles Meeting", 23);
        meeting1.setStartAt(LocalDateTime.of(2019, 5, 10, 10, 15));
        meeting1.setEndAt(LocalDateTime.of(2019, 5, 10, 11, 45));
        meeting1.setDescription("Tolle Beschreibung");
        meeting1.setOwner(user1);

        final Meeting meeting2 = new Meeting("Nicht so tolles Meeting", 18);
        meeting2.setStartAt(LocalDateTime.of(2019, 5, 11, 14, 0));
        meeting2.setEndAt(LocalDateTime.of(2019, 5, 11, 15, 0));
        meeting2.setDescription("Nicht so tolle Beschreibung");
        meeting2.setOwner(user2);

        final Meeting meeting3 = new Meeting("Geheimes Meeting", 3);
        meeting3.setStartAt(LocalDateTime.of(2019, 5, 12, 23, 0));
        meeting3.setEndAt(LocalDateTime.of(2019, 5, 12, 23, 30));
        meeting3.setOwner(user1);

        meetingService.createMeeting(
        meeting1.getTitle(), meeting1.getParticipantsNumber(),
        meeting1.getStartAt(), meeting1.getEndAt(), meeting1.getOwner());

        meetingService.createMeeting(
        meeting2.getTitle(), meeting2.getParticipantsNumber(),
        meeting2.getStartAt(), meeting2.getEndAt(), meeting2.getOwner());

        meetingService.createMeeting(
        meeting3.getTitle(), meeting3.getParticipantsNumber(),
        meeting3.getStartAt(), meeting3.getEndAt(), meeting3.getOwner());
    }
}
