package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for managing the start page for each individual user.
 */
@Controller
public class StartPageController {
    @Autowired
    private MeetingService service;

    /**
     * Show the start page to the user.
     * @return Start Page ModelAndView Object.
     */
    @RequestMapping("/start")
    public ModelAndView showStart() {
        final ModelAndView modelAndView = new ModelAndView("startpage");

        modelAndView.addObject("meetings", service.getAllMeetings());
        return modelAndView;
    }


}
