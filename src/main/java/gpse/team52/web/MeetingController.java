package gpse.team52.web;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.ParticipantService;
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
import org.springframework.web.bind.annotation.*;
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
                                       @RequestParam(value = "action") String action,
                                       @ModelAttribute("addParticipants")
                                       @Valid final MeetingAddParticipantsForm addParticipants,
                                       final BindingResult bindingResult,
                                       final Authentication authentication) {
        final Meeting meeting = meetingService.getMeetingById(id);
        if (action.equals("add")) {
            if (!bindingResult.hasErrors()) {
                try {
                    addAllParticipants(meeting, addParticipants);
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
        }
        return generateMeetingOverviewView(meeting, (User) authentication.getPrincipal());

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

    private void addAllParticipants(final Meeting meeting, final MeetingAddParticipantsForm form)
    throws ParticipantAlreadyExistsException, ExternalUserIsIncompleteException {
        final List<Participant> participants = new ArrayList<>();

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

        meetingService.addParticipants(meeting, participants);
    }

    private void addExistingParticipants(final List<Participant> participants, final List<String> userList) {
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
        boolean activate = false;
        final LocalDateTime currenttime = LocalDateTime.now();
        final LocalDateTime meetingtime = meeting.getStartAt();
        final long diff = Duration.between(currenttime, meetingtime).toMinutes();
        if (diff <= 30 && !meeting.isConfirmed()) {
            activate = true;
        }
        return activate;
    }
}
