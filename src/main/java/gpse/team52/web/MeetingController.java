package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @GetMapping("/meeting/{id}")
    public ModelAndView meeting(@PathVariable("id") final String id, Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("meeting");

        Meeting meeting = meetingService.getMeetingById(id);
        modelAndView.addObject("meeting", meeting);

        User user = (User) authentication.getPrincipal();
        if (user.getUsername() == meeting.getOwner().getUsername()) {
            System.out.println(user.getUsername());
            /*
        List<Participant> participants = meeting.getParticipants();
        System.out.println(participants.get(0).getFirstName());
        modelAndView.addObject("participants", participants);
         */
        }

        return modelAndView;
    }
}
