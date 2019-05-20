package gpse.team52.contract;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;

/**
 * Meeting Service.
 */
public interface MeetingService {

    Meeting createMeeting(String title, int participants,
                          LocalDateTime start, LocalDateTime end,
                          User owner, Room room);

    Meeting createMeeting(Meeting meeting);

    Iterable<Meeting> getAllMeetings();

    Meeting getMeetingById(UUID id);

    Meeting getMeetingById(String id);

    Iterable<Meeting> findByStartAtBetween(LocalDateTime start, LocalDateTime end);
}
