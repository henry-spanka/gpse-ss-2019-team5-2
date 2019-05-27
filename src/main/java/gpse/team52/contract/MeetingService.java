package gpse.team52.contract;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.exception.ExternalUserIsIncompleteException;
import gpse.team52.exception.ParticipantAlreadyExistsException;
import gpse.team52.form.MeetingCreationForm;

/**
 * Meeting Service.
 */
public interface MeetingService {

    Meeting createMeeting(String title, int participants,
                          LocalDateTime start, LocalDateTime end,
                          User owner, Room room);

    Meeting createMeeting(Meeting meeting);

    Meeting createMeeting(Meeting meeting, Room room, int participants);

    Meeting createMeeting(MeetingCreationForm meetingForm, List<Room> rooms,
                          Map<String, Integer> participants, User owner);

    Iterable<Meeting> getAllMeetings();

    Meeting getMeetingById(UUID id);

    Meeting getMeetingById(String id);

<<<<<<< HEAD
    Iterable<Meeting> findByStartAtBetweenAndParticipantsIn(LocalDateTime start, LocalDateTime end, Iterable<Participant>meetingpart);

=======
    Iterable<Meeting> findByStartAtBetween(LocalDateTime start, LocalDateTime end);

    Meeting addParticipants(Meeting meeting, List<Participant> participants)
    throws ParticipantAlreadyExistsException, ExternalUserIsIncompleteException;
>>>>>>> develop
}
