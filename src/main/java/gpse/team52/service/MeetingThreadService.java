package gpse.team52.service;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class MeetingThreadService {

    @Autowired
    MeetingService meetingService;

    @Autowired
    private TaskExecutor taskExecutor;

    public MeetingThreadService(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    public void executeTask() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    checkMeetings();
                } catch(InterruptedException e) {
                    run();
                }

            }
        });
    }

    @Async
    public void checkMeetings() throws InterruptedException{
        ArrayList<Meeting> meetings = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        while(true) {
            meetingService.findByConfirmed(false).forEach(meetings::add);
            for (Meeting meeting: meetings) {
                LocalDateTime meetingstart = meeting.getStartAt();
                long diff = Duration.between(now, meetingstart).toMinutes();
                if (diff == 30) {
                    User user = meeting.getOwner();
                    sendConfirmationEmail(user, meeting);
                } else if (diff < 0 && !meeting.isConfirmed()) {
                    meetingService.deleteByMeetingId(meeting.getMeetingId());
                }
            }
            Thread.sleep(60000); //recheck every minute
        }
    }

    private void sendConfirmationEmail(User user, Meeting meeting) throws MailException {
        meetingService.sendConfirmationEmail(user, meeting);
    }
}
