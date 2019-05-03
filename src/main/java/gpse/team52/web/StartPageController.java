package gpse.team52.web;

import gpse.team52.contract.StartMeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for managing the start page for each individual user.
 */
@Controller
public class StartPageController {

    private final StartMeetingService startMeetingService;

    @Autowired
    public StartPageController(final StartMeetingService startMeetingService) {
        this.startMeetingService = startMeetingService;
    }

    @RequestMapping("/start")
    public ModelAndView showStart() {
        final ModelAndView modelAndView = new ModelAndView("startpage");

        return modelAndView;
    }


}
