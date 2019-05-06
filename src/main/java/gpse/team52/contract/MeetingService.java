package gpse.team52.contract;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;

import java.time.LocalDateTime;

public interface MeetingService {

    Meeting createMeeting(String title, int participants, LocalDateTime start, LocalDateTime end, User owner);

    Iterable<Meeting> getAllMeetings();
}
