package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for handling logout requests.
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

}
