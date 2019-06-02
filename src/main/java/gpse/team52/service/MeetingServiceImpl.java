package gpse.team52.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.*;
import gpse.team52.exception.InvalidConfirmationTokenException;
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
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailService mailService;

    @Autowired
    public MeetingServiceImpl(final MeetingRepository meetingRepository,
                              final ParticipantRepository participantRepository,
                              final ConfirmationTokenRepository confirmationTokenRepository,
                              final MailService mailService) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
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
    public Meeting createMeeting(final MeetingCreationForm meetingForm, final List<Room> rooms,
                                 final Map<String, Integer> participants, final User owner) {
        final Meeting meeting = new Meeting(meetingForm.getName());
        meeting.setStartAt(meetingForm.getStartDateTime());
        meeting.setEndAt(meetingForm.getEndDateTime());
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
    public Iterable<Meeting> findByStartAtBetweenAndParticipantsIn(LocalDateTime start, LocalDateTime end, Iterable<Participant> meetingpart) {
        return meetingRepository.findByStartAtBetweenAndParticipantsIn(start, end, meetingpart);
    }

    @Override
    public Iterable<Meeting> findByStartAt() {
        return meetingRepository.findByOrderByStartAtAsc();
    }

    @Override
    public Iterable<Meeting> findByStartAtWithUser(User user) {
        List<Meeting> finalMeetings = new ArrayList<>();

        List<Meeting> meetings = (List) findByStartAt();
        for (int i = 0; i < meetings.size(); i++) {
            List<Participant> participants = meetings.get(i).getParticipants();
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
        }

        return meetingRepository.save(meeting);
    }

    @Override
    public Meeting validateMeetingFromToken(final UUID token) throws InvalidConfirmationTokenException {
        final ConfirmationToken confirmationToken = confirmationTokenRepository.findById(token)
        .orElseThrow(() -> new InvalidConfirmationTokenException("The token " + token + " is invalid."));

        final Meeting meeting = confirmationToken.getMeeting();
        meeting.setConfirmed(true);
        confirmationTokenRepository.delete(confirmationToken);

        return meeting;
    }

    /**
     * Send a confirmation email to the user's email address.
     *
     * @param user The User is the owner.
     * @param meeting The meeting to confirm.
     */
    @Override
    public void sendVerificationEmail(final User user, final Meeting meeting) {
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        final ModelAndView modelAndView = new ModelAndView("email/confirm-meeting", "meeting", meeting);
        modelAndView.addObject("token", confirmationToken);

        mailService.sendEmailTemplateToUser(user, "Meeting Confirmation", modelAndView);
    }
}
