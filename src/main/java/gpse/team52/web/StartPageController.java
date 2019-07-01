package gpse.team52.web;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import gpse.team52.contract.LocationService;
import gpse.team52.contract.MeetingService;
import gpse.team52.contract.ParticipantService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * Responsible for managing the start page for each individual user.
 */
@Controller
public class StartPageController {
    private static final String MONDAY = "MON";
    private static final String TUESDAY = "TUE";
    private static final String WEDNESDAY = "WED";
    private static final String THURSDAY = "THU";
    private static final String FRIDAY = "FRI";
    private static final String SATURDAY = "SAT";
    private static final String SUNDAY = "SUN";

    @Autowired
    private MeetingService meetingService;
    @Autowired
    private ParticipantService participantService;

    @Autowired
    private LocationService locationService;


    public StartPageController(final MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * Show the start page to the user.
     * Filters meetings based on logged in user and adjust the days based on the current day.
     *
     * @param authentication Authentication.
     * @return Start Page ModelAndView Object.
     */
    @GetMapping("/start")
    public ModelAndView showStart(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("startpage");
        final User user = (User) authentication.getPrincipal();

        final LocalDate today = LocalDate.now();
        final LocalDateTime starttoday = today.atStartOfDay();
        final LocalDateTime endtoday = today.plusDays(1).atStartOfDay();

        final LocalDate tomorrow = LocalDate.now().plusDays(1);
        final LocalDateTime starttomorrow = tomorrow.atStartOfDay();
        final LocalDateTime endtomorrow = tomorrow.plusDays(1).atStartOfDay();

        final LocalDate aftertomorrow = LocalDate.now().plusDays(2);
        final LocalDateTime startaftertomorrow = aftertomorrow.atStartOfDay();
        final LocalDateTime endaftertomorrow = aftertomorrow.plusDays(1).atStartOfDay();

        final ArrayList<Participant> participants = new ArrayList<>();
        participantService.findByUser(user).forEach(participants::add);

        final ArrayList<Meeting> meetingstoday = new ArrayList<>();
        final ArrayList<Meeting> meetingstomorrow = new ArrayList<>();
        final ArrayList<Meeting> meetingsaftertomorrow = new ArrayList<>();
        meetingService.findByStartAtBetweenAndParticipantsIn(starttoday, endtoday, participants)
        .forEach(meetingstoday::add);
        meetingService.findByStartAtBetweenAndParticipantsIn(starttomorrow, endtomorrow, participants)
        .forEach(meetingstomorrow::add);
        meetingService.findByStartAtBetweenAndParticipantsIn(startaftertomorrow, endaftertomorrow, participants)
        .forEach(meetingsaftertomorrow::add);

        if (!meetingstoday.isEmpty()) {
            modelAndView.addObject("meetings1", meetingstoday);
        }
        if (!meetingstomorrow.isEmpty()) {
            modelAndView.addObject("meetings2", meetingstomorrow);
        }
        if (!meetingsaftertomorrow.isEmpty()) {
            modelAndView.addObject("meetings3", meetingsaftertomorrow);
        }

        final String emptyday = "There are no meetings for today!";
        modelAndView.addObject("emptytday", emptyday);

        //dynamic names for upcomming days
        String daytom;
        String dayaftertom;

        final DayOfWeek daytomorrow = tomorrow.getDayOfWeek();

        switch (daytomorrow) {
            case MONDAY:
                daytom = MONDAY;
                dayaftertom = TUESDAY;
                break;
            case TUESDAY:
                daytom = TUESDAY;
                dayaftertom = WEDNESDAY;
                break;
            case WEDNESDAY:
                daytom = WEDNESDAY;
                dayaftertom = THURSDAY;
                break;
            case THURSDAY:
                daytom = THURSDAY;
                dayaftertom = FRIDAY;
                break;
            case FRIDAY:
                daytom = FRIDAY;
                dayaftertom = SATURDAY;
                break;
            case SATURDAY:
                daytom = SATURDAY;
                dayaftertom = SUNDAY;
                break;
            default:
                daytom = SUNDAY;
                dayaftertom = MONDAY;
                break;
        }

        modelAndView.addObject("tomorrow", daytom);
        modelAndView.addObject("aftertomorrow", dayaftertom);
        final long noLoctimediff = 0;
        if (user.getLocation() == null) {
            modelAndView.addObject("timeZone", noLoctimediff);
        } else {
            final long timediff = user.getLocation().getTimeoffset();
            modelAndView.addObject("timeZone", timediff);
        }


        return modelAndView;
    }
}
