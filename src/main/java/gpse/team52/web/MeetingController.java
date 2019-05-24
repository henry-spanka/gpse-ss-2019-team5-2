package gpse.team52.web;

import java.util.ArrayList;
import java.util.List;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

/**
 * Meeting controller.
 */
@Controller
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    /**
     * Provides data for the meeting.html.
     * @param id Id of the meeting that is shown in detail
     * @param authentication Needed to check if current user is Owner to display different data
     * @return
     */
    @GetMapping("/meeting/{id}")
    public ModelAndView meeting(@PathVariable("id") final String id, final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("meeting");

        Meeting meeting = meetingService.getMeetingById(id);
        modelAndView.addObject("meeting", meeting);

        //List<Equipment> equipments = meeting.getRoom().getEquipment();
        List<Equipment> equipments = new ArrayList<>();

        modelAndView.addObject("equipments", equipments);

        User user = (User) authentication.getPrincipal();
        if (user.getUsername() == meeting.getOwner().getUsername()) {
            List<Participant> participants = meeting.getParticipants();
            modelAndView.addObject("participants", participants);
        }

        return modelAndView;
    }
}
