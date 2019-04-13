package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for handling logout requests.
 */
@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView login(
    @RequestParam(name = "error", required = false) String error,
    @RequestParam(name = "logout", required = false) String logout) {
        return new ModelAndView("login")
        .addObject("error", error != null)
        .addObject("logout", logout != null);
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("logout");
    }

}
