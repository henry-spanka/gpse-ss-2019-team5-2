package gpse.team52.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.ParticipantService;
import gpse.team52.contract.RoomFinderService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import gpse.team52.exception.ExternalUserIsIncompleteException;
import gpse.team52.exception.ParticipantAlreadyExistsException;
import gpse.team52.form.MeetingAddParticipantsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Meeting controller.
 */
@Controller
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomFinderService roomFinderService;

    /**
     * Provides data for the meeting.html.
     *
     * @param id             Id of the meeting that is shown in detail
     * @param authentication Needed to check if current user is Owner to display different data
     * @return Meeting Details page.
     */
    @GetMapping("/meeting/{id}")
    public ModelAndView meeting(@PathVariable("id") final String id, final Authentication authentication) {
        final Meeting meeting = meetingService.getMeetingById(id);

        return generateMeetingOverviewView(meeting, (User) authentication.getPrincipal());
    }

    /**
     * Add new participants to an existing meeting.
     *
     * @param id              The id of the meeting.
     * @param action          Either add participants or confirm meeting
     * @param addParticipants The participants to add.
     * @param bindingResult   The result of the validation.
     * @param authentication  The logged in user.
     * @return Meeting details page or an error.
     */
    @PostMapping("/meeting/{id}")
    public ModelAndView addParticipant(@PathVariable("id") final String id,
                                       final @RequestParam("action") String action,
                                       @ModelAttribute("addParticipants")
                                       @Valid final MeetingAddParticipantsForm addParticipants,
                                       final BindingResult bindingResult,
                                       final Authentication authentication) {
        Meeting meeting = meetingService.getMeetingById(id); //NOPMD
        if (action.equals("add")) {
            if (!bindingResult.hasErrors()) {
                try {
                    meeting = addAllParticipants(meeting, addParticipants);
                } catch (ParticipantAlreadyExistsException e) {
                    bindingResult.rejectValue("participants", "meeting.participants.exists", e.getMessage());
                } catch (ExternalUserIsIncompleteException e) {
                    bindingResult.rejectValue("firstName", "meeting.participants.externalIncomplete", e.getMessage());
                }
            }

            // Check again as we may have had some errors during persisting them to the database.
            if (!bindingResult.hasErrors()) {
                return generateMeetingOverviewView(meeting, (User) authentication.getPrincipal());
            }
        }

        if (action.equals("confirm")) {
            meeting.setConfirmed(true);
            meetingService.update(meeting);
        }
        return generateMeetingOverviewView(meeting, (User) authentication.getPrincipal());

    }

    /**
     * Change a participant's notification options.
     * @param id Id of the meeting.
     * @param pId Id of the participant.
     * @return Meeting overview page.
     */
    @PatchMapping("/meeting/{id}/participant/{pId}")
    public ModelAndView editParticipant(@PathVariable("id") final String id, @PathVariable("pId") final String pId) {
        final Meeting meeting = meetingService.getMeetingById(id);
        Participant participant = participantService.findParticipantById(UUID.fromString(pId)).orElseThrow();

        participant.setNotifiable(!participant.isNotifiable());

        participant = participantService.update(participant);

        if (participant.isNotifiable()) {
            meetingService.notifyParticipant(meeting, participant);
        }

        return new ModelAndView("redirect:/meeting/" + meeting.getMeetingId());
    }

    /**
     * Deletes a participant from the meeting.
     *
     * @param id  Meeting Id.
     * @param pId Participant Id.
     * @return Redirects back to the meeting detail page.
     */
    @DeleteMapping("/meeting/{id}/participant/{pId}")
    public ModelAndView removeParticipant(@PathVariable("id") final String id, @PathVariable("pId") final String pId) {
        final Meeting meeting = meetingService.getMeetingById(id);
        participantService.deleteById(UUID.fromString(pId));

        return new ModelAndView("redirect:/meeting/" + meeting.getMeetingId());
    }

    /**
     * Confirms a meeting via email token.
     * @param meetingId Given meeting token via email.
     * @return Redirects to the confirmed page to inform the user.
     */
    @GetMapping("/meeting-confirmed")
    public ModelAndView confirmMeeting(final @RequestParam("meeting") String meetingId) {
        final ModelAndView modelAndView = new ModelAndView("meeting-confirmed");
        final Meeting meeting = meetingService.getMeetingById(meetingId);
        meeting.setConfirmed(true);
        meetingService.confirmMeeting(UUID.fromString(meetingId));
        modelAndView.addObject("error", false);

        return modelAndView;
    }

    private ModelAndView generateMeetingOverviewView(final Meeting meeting, final User user) {
        return generateMeetingOverviewView(meeting, user, new MeetingAddParticipantsForm());
    }

    private ModelAndView generateMeetingOverviewView(final Meeting meeting, final User user,
                                                     final MeetingAddParticipantsForm form) {
        final ModelAndView modelAndView = new ModelAndView("meeting");

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("user", user);
        modelAndView.addObject("regUsers", userService.getAllUsers());
        modelAndView.addObject("addParticipants", form);
        modelAndView.addObject("availableRooms", roomFinderService.find(meeting.toMeetingCreationForm(), false));

        if (checkOwner(user, meeting)) {
            final boolean isOwner = true;
            modelAndView.addObject("isOwner", isOwner);
            if (checkConfirmButton(meeting)) {
                final boolean activate = true;
                modelAndView.addObject("activate", activate);
            }
            if (checkActivated(meeting)) {
                final boolean activated = true;
                modelAndView.addObject("activated", activated);
            }
        }

        return modelAndView;
    }

    private Meeting addAllParticipants(final Meeting meeting, final MeetingAddParticipantsForm form)
    throws ParticipantAlreadyExistsException, ExternalUserIsIncompleteException {
        final List<Participant> participants = new ArrayList<>(); //NOPMD

        if (form.getParticipants() != null) {
            addExistingParticipants(participants, form.getParticipants());
        }

        if (form.externalFilled()) {
            if (form.externalComplete()) {
                addExternalParticipant(participants, form.getFirstName(), form.getLastName(), form.getEmail());
            } else {
                throw new ExternalUserIsIncompleteException("The external user is missing some fields");
            }
        }

        return meetingService.addParticipants(meeting, participants);
    }

    private void addExistingParticipants(final List<Participant> participants, final List<String> userList) { //NOPMD
        for (final String userId : userList) {
            final User user = userService.getUserById(userId).orElseThrow();

            participants.add(new Participant(user)); //NOPMD
        }
    }

    private void addExternalParticipant(final List<Participant> participants, final String firstName,
                                        final String lastName, final String email) {
        participants.add(new Participant(email, firstName, lastName));
    }

    /**
     * Method to check if the user is the owner of the meeting.
     * @param user The user to check for.
     * @param meeting The meeting to check for.
     * @return True if user is owner, otherwise false.
     */
    private boolean checkOwner(final User user, final Meeting meeting) {
        return user.getUserId().equals(meeting.getOwner().getUserId());
    }

    /**
     * Method to check if the meeting ist laready confirmed.
     * @param meeting The meeting to check for.
     * @return True, if the meeting is already confirmed, otherwise false.
     */
    private boolean checkActivated(final Meeting meeting) {
        return meeting.isConfirmed();
    }

    /**
     * Method to check if the confirm button should be disabled or not.
     * @param meeting The meeting to check for.
     * @return True, if the meeting is soon, so the button can be activated, otherwise false.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private boolean checkConfirmButton(final Meeting meeting) {
        boolean activate = false; //NOPMD
        final LocalDateTime currenttime = LocalDateTime.now();
        final LocalDateTime meetingtime = meeting.getStartAt();
        final long diff = Duration.between(currenttime, meetingtime).toMinutes();
        if (diff <= 30 && !meeting.isConfirmed()) {
            activate = true;
        }
        return activate;
    }
}
