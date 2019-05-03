package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
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


    private MeetingService meetingService;

    @Autowired
    public InitializeDefaultMeetings(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * Initializes the default admin user if it doesn't exist already.
     */
    @PostConstruct
    public void init() {
        final Meeting meeting1 = new Meeting("Tolles Meeting", 23);
        meeting1.setStartAt(LocalDateTime.of(2019,05, 10, 10, 15));
        meeting1.setEndAt(LocalDateTime.of(2019,05, 10, 11, 45));
        meeting1.setDescription("Tolle Beschreibung");

        final Meeting meeting2 = new Meeting("Nicht so tolles Meeting", 18);
        meeting2.setStartAt(LocalDateTime.of(2019,05, 11, 14, 00));
        meeting2.setEndAt(LocalDateTime.of(2019,05, 11, 15, 00));
        meeting2.setDescription("Nicht so tolle Beschreibung");

        final Meeting meeting3 = new Meeting("Geheimes Meeting", 3);
        meeting3.setStartAt(LocalDateTime.of(2019,05, 12, 23, 00));
        meeting3.setEndAt(LocalDateTime.of(2019,05, 12, 23, 30));

        meetingService.createMeeting(meeting1.getTitle(), meeting1.getParticipantsNumber(), meeting1.getStartAt(), meeting1.getEndAt());
        meetingService.createMeeting(meeting2.getTitle(), meeting2.getParticipantsNumber(), meeting2.getStartAt(), meeting2.getEndAt());
        meetingService.createMeeting(meeting3.getTitle(), meeting3.getParticipantsNumber(), meeting3.getStartAt(), meeting3.getEndAt());
    }
}
