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
    /**
     * Login Web Endpoint.
     * @param error HTTP GET parameter that indicates that an error occurred in the previous request.
     * @param logout Set if the user successfully logged out and was redirected to the login page.
     * @return Login Page.
     */
    @GetMapping("/login")
    public ModelAndView login(
    final @RequestParam(name = "error", required = false) String error,
    final @RequestParam(name = "logout", required = false) String logout) {
        return new ModelAndView("login")
        .addObject("error", error != null)
        .addObject("logout", logout != null);
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("logout");
    }

}
