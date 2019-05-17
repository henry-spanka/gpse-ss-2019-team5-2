package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Displaying the Chronic to the user.
 */
@Controller
public class ChronikController {
    @Autowired
    private MeetingService service;

    /**
     * Show upcoming meetings.
     * @return Chronic View.
     */
    @GetMapping("/chronik")
    public ModelAndView chronik() {
        final ModelAndView modelAndView = new ModelAndView("chronik");

        modelAndView.addObject("meetings", service.getAllMeetings());

        return modelAndView;
    }
}
