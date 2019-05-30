package gpse.team52.web;

import gpse.team52.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Profile page controller
 */
@Controller
public class ProfileController {
    @GetMapping("/profile")
    public ModelAndView profile(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("profile");
         final User user = (User) authentication.getPrincipal();
         modelAndView.addObject("fullName",user.getFirstname() + " " + user.getLastname());
         modelAndView.addObject("email", user.getEmail());

        return modelAndView;


    }

}
