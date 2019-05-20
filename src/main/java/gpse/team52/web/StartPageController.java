package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        LocalDate today = LocalDate.now();
        LocalDateTime starttoday = today.atStartOfDay();
        LocalDateTime endtoday = today.plusDays(1).atStartOfDay();

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalDateTime starttomorrow = today.atStartOfDay();
        LocalDateTime endtomorrow = today.plusDays(1).atStartOfDay();

        LocalDate aftertomorrow = LocalDate.now().plusDays(2);
        LocalDateTime startaftertomorrow = today.atStartOfDay();
        LocalDateTime endaftertomorrow = today.plusDays(1).atStartOfDay();

        ArrayList<Meeting>meetingstoday = new ArrayList<Meeting>();
        ArrayList<Meeting>meetingstomorrow = new ArrayList<Meeting>();
        ArrayList<Meeting>meetingsaftertomorrow = new ArrayList<Meeting>();

        meetingService.findByStartAtBetween(starttoday, endtoday).forEach(meetingstoday::add); //todays meetings
        meetingService.findByStartAtBetween(starttomorrow, endtomorrow).forEach(meetingstomorrow::add); //tomorrows meetings
        meetingService.findByStartAtBetween(startaftertomorrow, endaftertomorrow).forEach(meetingsaftertomorrow::add); //after tomorrows meetings

        modelAndView.addObject("meetingstoday", meetingstoday);
        modelAndView.addObject("meetingstomorrow", meetingstomorrow);
        modelAndView.addObject("meetingsaftertomorrow", meetingsaftertomorrow);

        String daytom;
        String dayaftertom;

        DayOfWeek daytomorrow = tomorrow.getDayOfWeek();

        switch (daytomorrow) {
            case MONDAY:
                daytom = "MON";
                dayaftertom = "TUE";
            break;
            case TUESDAY:
                daytom = "TUE";
                dayaftertom = "WED";
            break;
            case WEDNESDAY:
                daytom = "WED";
                dayaftertom = "THU";
                break;
            case THURSDAY:
                daytom = "THU";
                dayaftertom = "FRI";
                break;
            case FRIDAY:
                daytom = "FRI";
                dayaftertom = "SAT";
                break;
            case SATURDAY:
                daytom = "SAT";
                dayaftertom = "SUN";
                break;
            default:
                daytom = "SUN";
                dayaftertom = "MON";
                break;
        }

        modelAndView.addObject("tomorrow", daytom);
        modelAndView.addObject("aftertomorrow", dayaftertom);

        return modelAndView;
    }


}
