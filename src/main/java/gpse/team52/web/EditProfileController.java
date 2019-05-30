package gpse.team52.web;

import gpse.team52.form.UserProfileForm;
import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Edit profile controller.
 */
@Controller
public class EditProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/editProfile")
    public ModelAndView editProfile(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("editProfile");
        final User user = (User) authentication.getPrincipal();
        modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
        modelAndView.addObject("email", user.getEmail());
        final UserProfileForm userCmd = new UserProfileForm();
        userCmd.setFirstname(user.getFirstname());
        userCmd.setLastname(user.getLastname());
        modelAndView.addObject("createUserCmd", userCmd);
        return modelAndView;
    }

    @PostMapping("/editProfile")
    public ModelAndView editProfile(@AuthenticationPrincipal final User user, @ModelAttribute("createUserCmd") final UserProfileForm userProfileForm) {
        user.setFirstname(userProfileForm.getFirstname());
        user.setLastname(userProfileForm.getLastname());
        System.out.println(userProfileForm.getFirstname());
        System.out.println(userProfileForm.getLastname());
        userService.updateUser(user);
        return new ModelAndView("redirect:/profile");
    }
}
