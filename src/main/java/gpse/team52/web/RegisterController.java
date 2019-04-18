package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for registering a user.
 */
@Controller
public class RegisterController {
    /**
     * Show the registration form to the user.
     * @return Register view.
     */
    @GetMapping("/register")
    public ModelAndView showRegister() {
        return new ModelAndView("register");
    }

    /**
     * Try to register a user.
     * @return A confirmation or an error.
     */
    @PostMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register-confirm");
    }
}
