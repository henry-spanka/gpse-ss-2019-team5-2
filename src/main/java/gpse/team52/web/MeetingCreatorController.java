package gpse.team52.web;

import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MeetingCreatorController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/createMeeting")
    public void checkBoxAction (@RequestParam String checkbox){

    }

    @GetMapping("/createMeeting")
    public ModelAndView createMeeting() {
        final ModelAndView modelAndView = new ModelAndView("createMeeting");

        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", roomService.getAllLocations());

        return modelAndView;
    }

    @GetMapping("/addParticipants")
    public ModelAndView addParticipants() {
        final ModelAndView modelAndView = new ModelAndView("addParticipants");

        modelAndView.addObject("users", userService.getAllUsers());

        return modelAndView;
    }

}
