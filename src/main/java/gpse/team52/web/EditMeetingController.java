package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Edit meeting controller.
 */
@Controller
public class EditMeetingController {

    @Autowired
    private MeetingService meetingService;

    /**
     * returns page to edit meetings.
     * @param id meetingId
     * @return
     */
    @GetMapping("/meeting/{id}/edit")
    public ModelAndView editMeeting(@PathVariable("id") final String id) {
        final Meeting meeting = meetingService.getMeetingById(id);
        ModelAndView modelAndView = new ModelAndView("editMeeting");
        modelAndView.addObject("meeting", meeting);

        return modelAndView;
    }
}
