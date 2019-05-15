package gpse.team52.web;

import gpse.team52.Command.CreateUserCmd;
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

@Controller
public class editProfileController {

    @Autowired
    private UserService userService;
    @GetMapping("/editProfile")
    public ModelAndView editProfile(Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("editProfile");
        User user = (User) authentication.getPrincipal();
        modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
        modelAndView.addObject("email", user.getEmail());
        CreateUserCmd userCmd = new CreateUserCmd();
        userCmd.setFirstname(user.getFirstname());
        userCmd.setLastname(user.getLastname());
        modelAndView.addObject("createUserCmd", userCmd);
        return modelAndView;
    }

        @PostMapping("/editProfile")
        public ModelAndView editProfile(@AuthenticationPrincipal final User user, @ModelAttribute("createUserCmd") final CreateUserCmd createUserCmd) {
            user.setFirstname(createUserCmd.getFirstname());
            user.setLastname(createUserCmd.getLastname());
            System.out.println(createUserCmd.getFirstname());
            System.out.println(createUserCmd.getLastname());
            userService.updateUser(user);
            return new ModelAndView("redirect:/profile");
        }



}
