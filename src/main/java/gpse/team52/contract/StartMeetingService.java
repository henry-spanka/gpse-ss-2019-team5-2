package gpse.team52.contract;

import gpse.team52.domain.Meeting;

import java.util.List;

public interface StartMeetingService {
    List<Meeting> getMeetings();
}
