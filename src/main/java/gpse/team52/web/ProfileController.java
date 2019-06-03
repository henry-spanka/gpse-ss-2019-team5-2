package gpse.team52.web;


import gpse.team52.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class ProfileController {
    /**
     *
     * @param authentication
     * @return
     */
    @GetMapping("/profile")
    public ModelAndView profile(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("profile");
        final User user = (User) authentication.getPrincipal();
        modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
        modelAndView.addObject("email", user.getEmail());

        // Only get the Role name after the "_"
        final String role = user.getAuthorities().toString();
        final int indexRole = role.indexOf("_");
        modelAndView.addObject("role", user.getAuthorities().toString().
        substring(indexRole +  1, role.length() - 1).toLowerCase());

        modelAndView.addObject("location", user.getLocation());

        final String userPicName = user.getPicture();
        if (userPicName != null) {

            modelAndView.addObject("pbPic", userPicName);
        }

        return modelAndView;


    }




}
