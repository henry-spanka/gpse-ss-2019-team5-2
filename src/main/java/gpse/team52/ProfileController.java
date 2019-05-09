package gpse.team52;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.repository.UserRepository;
import gpse.team52.service.UserServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public ModelAndView profile(Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("profile");
         User user = (User) authentication.getPrincipal();
         modelAndView.addObject("fullName",user.getFirstname()+" "+user.getLastname());
         modelAndView.addObject("email",user.getEmail());
        return modelAndView;


    }

}
