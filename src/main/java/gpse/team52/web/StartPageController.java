package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartPageController {
    @RequestMapping("/start")
    public ModelAndView showStart() {
        final ModelAndView modelAndView = new ModelAndView("startpage");

        return modelAndView;
    }


}
