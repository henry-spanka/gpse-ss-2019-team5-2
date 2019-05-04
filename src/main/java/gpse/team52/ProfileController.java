package gpse.team52;

import gpse.team52.domain.User;
import gpse.team52.repository.UserRepository;
import gpse.team52.service.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public ModelAndView dashboard() {
        final ModelAndView modelAndView = new ModelAndView("profile");

        return modelAndView;
    }

}
