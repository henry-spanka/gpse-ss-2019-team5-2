package gpse.team52.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import gpse.team52.contract.MeetingService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Meeting controller.
 */
@Controller
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

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
                                       @ModelAttribute("addParticipants") @Valid final MeetingAddParticipantsForm addParticipants,
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

    private ModelAndView generateMeetingOverviewView(Meeting meeting, User user) {
        return generateMeetingOverviewView(meeting, user, new MeetingAddParticipantsForm());
    }

    private ModelAndView generateMeetingOverviewView(Meeting meeting, User user, MeetingAddParticipantsForm form) {
        final ModelAndView modelAndView = new ModelAndView("meeting");

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("user", user);
        modelAndView.addObject("regUsers", userService.getAllUsers());
        modelAndView.addObject("addParticipants", form);

        return modelAndView;
    }

    private void addAllParticipants(Meeting meeting, MeetingAddParticipantsForm form)
    throws ParticipantAlreadyExistsException, ExternalUserIsIncompleteException {
        List<Participant> participants = new ArrayList<>();

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

    private void addExistingParticipants(List<Participant> participants, List<String> userList) {
        for (String userId : userList) {
            User user = userService.getUserById(userId).orElseThrow();

            participants.add(new Participant(user));
        }
    }

    private void addExternalParticipant(List<Participant> participants, String firstName, String lastName, String email) {
        participants.add(new Participant(email, firstName, lastName));
    }
}
