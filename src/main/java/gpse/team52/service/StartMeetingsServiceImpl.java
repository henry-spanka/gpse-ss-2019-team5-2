package gpse.team52.service;

import gpse.team52.contract.StartMeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StartMeetingsServiceImpl implements StartMeetingService {
    private final MeetingRepository meetingRepository;

    @Autowired
    public StartMeetingsServiceImpl(final MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public List<Meeting> getMeetings() {
        final List<Meeting> meetings = new ArrayList<>();

        meetingRepository.findAll().forEach(meetings::add);

        return meetings;
    }
}
