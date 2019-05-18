package gpse.team52.web;

import gpse.team52.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public ModelAndView profile(Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("profile");
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
        modelAndView.addObject("email", user.getEmail());
        return modelAndView;


    }

}
