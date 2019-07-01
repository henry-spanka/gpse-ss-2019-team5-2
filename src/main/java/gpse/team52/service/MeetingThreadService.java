package gpse.team52.service;


import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import gpse.team52.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


/**
 * MeetingThreadService.
 */
@Service
public class MeetingThreadService {

    private MeetingService meetingService;

    private ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

    private MeetingRepository meetingRepository;


    /**
     * Thread to check meetings in an intervall.
     *
     * @param meetingService    service to gain access to the meetingdb.
     * @param meetingRepository the repository to gain/save etc. the meeting.
     */
    @Autowired
    public MeetingThreadService(final MeetingService meetingService, final MeetingRepository meetingRepository) {

        this.meetingService = meetingService;
        this.meetingRepository = meetingRepository;
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

    /**
     * Checks every meeting and either sends confirmation email or deletes them.
     *
     * @throws InterruptedException in case of an Exception.
     */
    @Async
    public void checkMeetings() throws InterruptedException { //NOPMD
        while (true) {
            final ArrayList<Meeting> meetings = new ArrayList<>(); //NOPMD
            final LocalDateTime now = LocalDateTime.now(); //NOPMD

            meetingService.findByConfirmed(false).forEach(meetings::add);
            for (final Meeting meeting : meetings) {
                final LocalDateTime meetingstart = meeting.getStartAt();
                final long diff = Duration.between(now, meetingstart).toMinutes();
                if (diff <= 30 && !meeting.isConfirmemail()) {
                    final User user = meeting.getOwner();
                    sendConfirmationEmail(user, meeting);
                } else if (diff < 0 && !meeting.isConfirmed()) {
                    meetingService.deleteByMeetingId(meeting.getMeetingId());
                }
            }
            Thread.sleep(30000);
        }
    }

    private void sendConfirmationEmail(final User user, final Meeting meeting) throws MailException {
        meeting.setConfirmemail(true);
        meetingRepository.save(meeting);
        meetingService.sendConfirmationEmail(user, meeting);
    }
}
