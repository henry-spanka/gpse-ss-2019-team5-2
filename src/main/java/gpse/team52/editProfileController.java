package gpse.team52;

import gpse.team52.Command.CreateUserCmd;
import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import org.h2.command.ddl.CreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
        //modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
       // modelAndView.addObject("email", user.getEmail());
        modelAndView.addObject("createUserCmd", new CreateUserCmd());
        return modelAndView;
    }

        @PostMapping("/editProfile")
        public ModelAndView editProfile(@AuthenticationPrincipal final User user, final CreateUserCmd createUserCmd) {

         userService.changeFirstname(user, createUserCmd.getFirstname());

            return new ModelAndView("redirect:/profile");
        }



}
