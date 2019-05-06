package gpse.team52;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class editProfileController {
    @GetMapping("/editProfile")
    public ModelAndView editProfile(Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("editProfile");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        modelAndView.addObject("username",userDetails.getUsername());
        return modelAndView;
    }

}
