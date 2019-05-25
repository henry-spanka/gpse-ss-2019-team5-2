package gpse.team52.web;

import javax.validation.Valid;

import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.form.MeetingCreationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("meeting")
public class MeetingCreatorController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/createMeeting")
    public ModelAndView showRoomSelectionForm(@ModelAttribute("meeting") @Valid MeetingCreationForm meeting, final BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            //
        }

        return generateMeetingCreationView(meeting);
    }

    @GetMapping("/createMeeting")
    public ModelAndView showCreationForm(@ModelAttribute("meeting") MeetingCreationForm meeting) {
        return generateMeetingCreationView(meeting);
    }

    @ModelAttribute("meeting")
    public MeetingCreationForm createNewMeetingForm() {
        return new MeetingCreationForm();
    }

    private ModelAndView generateMeetingCreationView(MeetingCreationForm meeting) {
        final ModelAndView modelAndView = new ModelAndView("createMeeting");

        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("users", userService.getAllUsers());
        modelAndView.addObject("locations", roomService.getAllLocations());

        return modelAndView;
    }
}
