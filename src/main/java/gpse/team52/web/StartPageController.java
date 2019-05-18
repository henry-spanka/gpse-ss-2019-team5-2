package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Responsible for managing the start page for each individual user.
 */
@Controller
public class StartPageController {
    @Autowired
    private MeetingService meetingService;

    public StartPageController(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * Show the start page to the user.
     *
     * @return Start Page ModelAndView Object.
     */
    @RequestMapping("/start")
    public ModelAndView showStart(Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("startpage");
        User user = (User) authentication.getPrincipal();
        UUID userid = user.getUserId();
        meetingService.getAllMeetings().forEach();
        //modelAndView.addObject("meetingstoday", meetingService.
        //modelAndView.addObject("meetingstomorrow", meetingService.
        //modelAndView.addObject("meetingsaftertomorrow", meetingService.
        //modelAndView.addObject("meetings", meetingService.getAllMeetings());
        return modelAndView;
    }


}
