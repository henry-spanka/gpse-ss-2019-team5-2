package gpse.team52.service;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeetingServiceImpl implements MeetingService {

    private MeetingRepository repository;

    @Autowired
    public MeetingServiceImpl(MeetingRepository repository) {
        this.repository = repository;
    }


    @Override
    public Meeting createMeeting(String title, int participants, LocalDateTime start, LocalDateTime end) {
        Meeting meeting = new Meeting(title, participants);
        meeting.setStartAt(start);
        meeting.setEndAt(end);

        return repository.save(meeting);
    }

    @Override
    public Iterable<Meeting> getAllMeetings() {
        return repository.findAll();
    }
}
