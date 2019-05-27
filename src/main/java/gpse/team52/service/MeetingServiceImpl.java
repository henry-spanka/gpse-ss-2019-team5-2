package gpse.team52.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.MeetingRoom;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Meeting Service Implementation.
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingServiceImpl(final MeetingRepository repository) {
        this.meetingRepository = repository;
    }


    @Override
    public Meeting createMeeting(final String title, final int participants,
                                 final LocalDateTime start,
                                 final LocalDateTime end,
                                 final User owner, final Room room) {
        final Meeting meeting = new Meeting(title);

        meeting.setStartAt(start);
        meeting.setEndAt(end);
        meeting.setOwner(owner);

        return createMeeting(meeting, room, participants);
    }

    @Override
    public Meeting createMeeting(final Meeting meeting) {
        if (meeting.getDescription() == null) {
            meeting.setDescription("-");
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting createMeeting(final Meeting meeting, final Room room, final int participants) {
        final MeetingRoom meetingRoom = new MeetingRoom(meeting, room, participants);

        meeting.addRoom(meetingRoom);

        return createMeeting(meeting);
    }

    @Override
    public Meeting createMeeting(final MeetingCreationForm meetingForm, final List<Room> rooms,
                                 final Map<String, Integer> participants, final User owner) {
        final Meeting meeting = new Meeting(meetingForm.getName());
        meeting.setStartAt(meetingForm.getStartDateTime());
        meeting.setEndAt(meetingForm.getEndDateTime());
        meeting.setOwner(owner);

        for (final Room room: rooms) {
            final MeetingRoom meetingRoom = new MeetingRoom(meeting, room,
            participants.get(room.getLocation().getLocationId().toString())); //NOPMD
            meeting.addRoom(meetingRoom);
        }

        return createMeeting(meeting);
    }

    @Override
    public Iterable<Meeting> getAllMeetings() {
        return meetingRepository.findAll();
    }

    @Override
    public Meeting getMeetingById(final UUID id) {
        return meetingRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("No meeting with id: " + id + " found"));
    }

    @Override
    public Meeting getMeetingById(final String id) {
        return getMeetingById(UUID.fromString(id));
    }

    @Override
    public Iterable<Meeting> findByStartAtBetween(LocalDateTime start, LocalDateTime end) {
        return meetingRepository.findByStartAtBetween(start, end);
    }
}
