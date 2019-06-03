package gpse.team52.service;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class MeetingThreadService {

    private MeetingService meetingService;

    private ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    @Autowired
    public MeetingThreadService(MeetingService meetingService) {

        this.meetingService = meetingService;
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(2);
        taskExecutor.setThreadNamePrefix("MeetingCheck");
        taskExecutor.initialize();
        executeTask();
    }

    private void executeTask() {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    checkMeetings();
                } catch (InterruptedException e) {
                    run();
                }

            }
        });
    }

    @Async
    public void checkMeetings() throws InterruptedException {
        ArrayList<Meeting> meetings = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        meetingService.findByConfirmed(false).forEach(meetings::add);
        for (Meeting meeting : meetings) {
            LocalDateTime meetingstart = meeting.getStartAt();
            long diff = Duration.between(now, meetingstart).toMinutes();
            if (diff == 30) {
                User user = meeting.getOwner();
                sendConfirmationEmail(user, meeting);
                System.out.println(meeting.getTitle() + ": Email wird verschickt!");
            } else if (diff < 0 && !meeting.isConfirmed()) {
                meetingService.deleteByMeetingId(meeting.getMeetingId());
                System.out.println(meeting.getTitle() + " wird geloescht!");
            }
        }
        Thread.sleep(60000);
        System.out.println("Thread running");
    }

    private void sendConfirmationEmail(User user, Meeting meeting) throws MailException {
        meetingService.sendConfirmationEmail(user, meeting);
    }
}
