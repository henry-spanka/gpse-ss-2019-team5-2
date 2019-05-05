package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MeetingCreatorController {


    @PostMapping("/createMeeting")
    public void checkBoxAction (@RequestParam String checkbox){

    }

    @GetMapping("/createMeeting")
    public ModelAndView createMeeting() {
        final ModelAndView modelAndView = new ModelAndView("createMeeting");

        return modelAndView;
    }
}
