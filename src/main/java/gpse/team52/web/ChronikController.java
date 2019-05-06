package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChronikController {

    @Autowired
    private MeetingService service;
    @GetMapping("/chronik")
    public ModelAndView dashboard() {
        final ModelAndView modelAndView = new ModelAndView("chronik");

        modelAndView.addObject("meetings", service.getAllMeetings());

        return modelAndView;
    }
}
