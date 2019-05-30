package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Displaying the Chronic to the user.
 */
@Controller
public class ChronikController {
    @Autowired
    private MeetingService meetingService;

    /**
     * Show upcoming meetings.
     *
     * @param authentication Only get meetings where logged in User participates
     * @return Chronic View.
     */
    @GetMapping("/chronik")
    public ModelAndView chronik(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("chronik");

        final User user = (User) authentication.getPrincipal();

        Iterable<Meeting> meetings = meetingService.findByStartAt();

        modelAndView.addObject("meetings", meetings);

        return modelAndView;
    }
}
