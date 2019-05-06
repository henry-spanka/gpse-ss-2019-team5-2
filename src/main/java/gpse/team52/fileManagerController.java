package gpse.team52;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class fileManagerController {
    @GetMapping("/fileManager")
    public ModelAndView dashboard() {
        final ModelAndView modelAndView = new ModelAndView("fileManager");
        return modelAndView;
    }
}
