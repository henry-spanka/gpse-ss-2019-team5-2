package gpse.team52.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.*;
import gpse.team52.exception.ParticipantAlreadyExistsException;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.repository.ConfirmationTokenRepository;
import gpse.team52.repository.MeetingRepository;
import gpse.team52.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * Meeting Service Implementation.
 */
@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final ParticipantRepository participantRepository;
    private final MailService mailService;

    @Autowired
    public MeetingServiceImpl(final MeetingRepository meetingRepository,
                              final ParticipantRepository participantRepository,
                              final MailService mailService) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
        this.mailService = mailService;
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
    public void update(final Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Override
    public Meeting createMeeting(final MeetingCreationForm meetingForm, final List<Room> rooms,
                                 final Map<String, Integer> participants, final User owner) {
        final Meeting meeting = new Meeting(meetingForm.getName());

        LocalDateTime startAt = meetingForm.getStartDateTime();
        LocalDateTime endAt = meetingForm.getEndDateTime();

        /**
         * This is hacky and probably doesn't work with daylight savings time but it's faster than
         * replacing all LocalDateTime instances with ZonedDateTime.
         */
        if (owner.getLocation() != null) {
            long timeOffset = owner.getLocation().getTimeoffset();
            System.out.println(timeOffset);

            startAt = startAt.minusMinutes(timeOffset);
            endAt = endAt.minusMinutes(timeOffset);
        }

        meeting.setStartAt(startAt);
        meeting.setEndAt(endAt);
        meeting.setOwner(owner);

        for (final Room room : rooms) {
            final MeetingRoom meetingRoom = new MeetingRoom(meeting, room, //NOPMD
            participants.get(room.getLocation().getLocationId().toString())); //NOPMD
            meeting.addRoom(meetingRoom);
        }

        meeting.addParticipant(new Participant(owner));

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
    public Iterable<Meeting> findByStartAtBetweenAndParticipantsIn(final LocalDateTime start, final LocalDateTime end, final Iterable<Participant> meetingpart) {
        return meetingRepository.findByStartAtBetweenAndParticipantsIn(start, end, meetingpart);
    }

    @Override
    public Iterable<Meeting> findByStartAt() {
        return meetingRepository.findByOrderByStartAtAsc();
    }

    @Override
    public Iterable<Meeting> findByStartAtWithUser(final User user) {
        final List<Meeting> finalMeetings = new ArrayList<>();

        final List<Meeting> meetings = (List) findByStartAt();
        for (int i = 0; i < meetings.size(); i++) {
            final List<Participant> participants = meetings.get(i).getParticipants();
            for (int j = 0; j < participants.size(); j++) {
                if (participants.get(j).isUser()) {
                    if (participants.get(j).getUser().getUserId().equals(user.getUserId())) {
                        finalMeetings.add(meetings.get(i));
                        break;
                    }
                }
            }
        }
        return finalMeetings;
    }

    @Override
    public void deleteByMeetingId(final UUID id) {
        meetingRepository.deleteById(id);
    }

    @Override
    public Iterable<Meeting> findByConfirmed(final boolean bool) {
        return meetingRepository.findByConfirmed(bool);
    }

    /**
     * Add a list of participants to a meeting.
     *
     * @param meeting      The meeting.
     * @param participants The participants list.
     * @return The saved meeting.
     * @throws ParticipantAlreadyExistsException Thrown if the participant already exists.
     */
    @Override
    public Meeting addParticipants(final Meeting meeting, final List<Participant> participants)
    throws ParticipantAlreadyExistsException {
        for (final Participant participant : participants) {
            if (meeting.getParticipants().contains(participant)) {
                throw new ParticipantAlreadyExistsException("Already participating in the meeting.");
            }
            meeting.addParticipant(participant);
            participant.setMeeting(meeting);

            ModelAndView mailView = new ModelAndView("email/added-to-meeting.html", "meeting", meeting);

            mailService.sendEmailTemplate(participant, "Added to meeting", mailView);
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public void confirmMeeting(final UUID meetingId) {
        final Meeting meeting = getMeetingById(meetingId);

        meeting.setConfirmed(true);
        meetingRepository.save(meeting);
    }

    /**
     * Send a confirmation email to the user's email address.
     *
     * @param user The User is the owner.
     * @param meeting The meeting to confirm.
     */
    @Override
    public void sendConfirmationEmail(final User user, final Meeting meeting) {

        final ModelAndView modelAndView = new ModelAndView("email/confirm-meeting", "meeting", meeting);
        modelAndView.addObject("meetingid", meeting);

        mailService.sendEmailTemplateToUser(user, "Meeting Confirmation", modelAndView);
    }
}
