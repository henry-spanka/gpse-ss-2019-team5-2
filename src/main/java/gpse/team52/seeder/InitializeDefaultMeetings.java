package gpse.team52.seeder;

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

import java.time.LocalDateTime;

/**
 * Initializes the Default User in the database.
 */
@Service
public class InitializeDefaultMeetings {

    private static final String DEFAULT_PASSWORD = "test";
    private MeetingService meetingService;

    private UserService userService;

    @Autowired
    public InitializeDefaultMeetings(final MeetingService meetingService, final UserService userService) {
        this.meetingService = meetingService;
        this.userService = userService;
    }

    /**
     * Initializes the default admin user if it doesn't exist already.
     */
    @PostConstruct
    public void init() {
        final UserRegistrationForm form = new UserRegistrationForm();
        form.setFirstName("Julius");
        form.setLastName("Ellermann");
        form.setEmail("jellermann@example.org");
        form.setUsername("julius");
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        User user;

        try {
            user = userService.createUser(form, true, "ROLE_USER");
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
            return;
        }

        final Meeting meeting1 = new Meeting("Tolles Meeting", 23);
        meeting1.setStartAt(LocalDateTime.of(2019,05, 10, 10, 15));
        meeting1.setEndAt(LocalDateTime.of(2019,05, 10, 11, 45));
        meeting1.setDescription("Tolle Beschreibung");
        meeting1.setOwner(user);

        final Meeting meeting2 = new Meeting("Nicht so tolles Meeting", 18);
        meeting2.setStartAt(LocalDateTime.of(2019,05, 11, 14, 00));
        meeting2.setEndAt(LocalDateTime.of(2019,05, 11, 15, 00));
        meeting2.setDescription("Nicht so tolle Beschreibung");
        meeting2.setOwner(user);

        final Meeting meeting3 = new Meeting("Geheimes Meeting", 3);
        meeting3.setStartAt(LocalDateTime.of(2019,05, 12, 23, 00));
        meeting3.setEndAt(LocalDateTime.of(2019,05, 12, 23, 30));
        meeting3.setOwner(user);

        meetingService.createMeeting(meeting1.getTitle(), meeting1.getParticipantsNumber(), meeting1.getStartAt(), meeting1.getEndAt(), meeting1.getOwner());
        meetingService.createMeeting(meeting2.getTitle(), meeting2.getParticipantsNumber(), meeting2.getStartAt(), meeting2.getEndAt(), meeting2.getOwner());
        meetingService.createMeeting(meeting3.getTitle(), meeting3.getParticipantsNumber(), meeting3.getStartAt(), meeting3.getEndAt(), meeting3.getOwner());
    }
}
