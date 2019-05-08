package gpse.team52.service;

import java.time.LocalDateTime;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Meeting Service Implementation.
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository repository;

    @Autowired
    public MeetingServiceImpl(final MeetingRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meeting createMeeting(final String title, final int participants, final LocalDateTime start,
                                 final LocalDateTime end, final User owner, final Room room) {
        final Meeting meeting = new Meeting(title, participants, room);
        meeting.setStartAt(start);
        meeting.setEndAt(end);
        meeting.setOwner(owner);

        return createMeeting(meeting);
    }

    @Override
    public Meeting createMeeting(Meeting meeting) {
        return repository.save(meeting);
    }

    @Override
    public Iterable<Meeting> getAllMeetings() {
        return repository.findAll();
    }
}
