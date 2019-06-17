package gpse.team52.web;

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
     * @param addParticipants The participants to add.
     * @param bindingResult   The result of the validation.
     * @param authentication  The logged in user.
     * @return Meeting details page or an error.
     */
    @PostMapping("/meeting/{id}")
    public ModelAndView addParticipant(@PathVariable("id") final String id,
                                       @ModelAttribute("addParticipants")
                                       @Valid final MeetingAddParticipantsForm addParticipants,
                                       final BindingResult bindingResult,
                                       final Authentication authentication) {
        final Meeting meeting = meetingService.getMeetingById(id);

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

        return generateMeetingOverviewView(meeting, (User) authentication.getPrincipal(), addParticipants);
    }

    /**
     * Deletes a participant from the meeting.
     * @param id Meeting Id.
     * @param pId Participant Id.
     * @return Redirects back to the meeting detail page.
     */
    @DeleteMapping("/meeting/{id}/participant/{pId}")
    public ModelAndView removeParticipant(@PathVariable("id") final String id, @PathVariable("pId") final String pId) {
        final Meeting meeting = meetingService.getMeetingById(id);
        participantService.deleteById(UUID.fromString(pId));

        return new ModelAndView("redirect:/meeting/" + meeting.getMeetingId());
    }

    private ModelAndView generateMeetingOverviewView(final Meeting meeting, final User user) {
        return generateMeetingOverviewView(meeting, user, new MeetingAddParticipantsForm());
    }

    private ModelAndView generateMeetingOverviewView(final Meeting meeting, final User user,
                                                     final MeetingAddParticipantsForm form) {
        final ModelAndView modelAndView = new ModelAndView("meeting");

        long NoLoctimediff = 0;
        if(user.getLocation()!=null) {
            long timediff = user.getLocation().getTimeoffset();
            modelAndView.addObject("timeZone", timediff);
        }
        else {
            modelAndView.addObject("timeZone", NoLoctimediff);
        }


        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("user", user);
        modelAndView.addObject("regUsers", userService.getAllUsers());
        modelAndView.addObject("addParticipants", form);

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
}
