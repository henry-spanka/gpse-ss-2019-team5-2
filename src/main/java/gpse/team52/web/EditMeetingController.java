package gpse.team52.web;

import gpse.team52.contract.*;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.Room;
import gpse.team52.domain.User;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.form.MeetingCreationForm;
import gpse.team52.form.MeetingEditorForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Edit meeting controller.
 */
@Controller
@SessionAttributes("meeting")
public class EditMeetingController {

    @Autowired
    private MeetingService meetingService;



    /**
     * returns page to edit meetings.
     *
     * @param id meetingId
     * @return
     */
    @GetMapping("/meeting/{id}/edit")
    public ModelAndView editMeeting(@PathVariable("id") final String id) {
        final Meeting meeting = meetingService.getMeetingById(id);
        ModelAndView modelAndView = new ModelAndView("editMeeting");
        modelAndView.addObject("meeting", meeting);
        final MeetingEditorForm editedMeeting = new MeetingEditorForm();
        editedMeeting.setName(meeting.getTitle());
        modelAndView.addObject("editedMeeting",editedMeeting);

        return modelAndView;
    }

    @PatchMapping("/meeting/{id}")
    public ModelAndView bookEditedMeeting(@PathVariable("id") final String id,
    final @ModelAttribute("editedMeeting") @Validated MeetingEditorForm editedMeeting)
    {
        final Meeting meeting = meetingService.getMeetingById(id);
        meeting.setTitle(editedMeeting.getName());
        meetingService.update(meeting);

        return new ModelAndView("redirect:/meeting/" + id);

    }

}
