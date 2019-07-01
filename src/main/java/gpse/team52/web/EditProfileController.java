package gpse.team52.web;

import java.util.ArrayList;

import gpse.team52.command.CreateUserCmd;
import gpse.team52.contract.LocationService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Location;
import gpse.team52.domain.User;
import gpse.team52.repository.LocationRepository;
import gpse.team52.service.DBFileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


/**
 * Edit Profile Controller.
 */
@Controller
public class EditProfileController {

    @Autowired
    private DBFileStorageServiceImpl dbFileStorageService;
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationRepository locationRepository;


    /**
     * @param authentication Authentication.
     * @return ModelAndView.
     */
    @GetMapping("/editProfile")
    public ModelAndView editProfile(final Authentication authentication) {
        final ModelAndView modelAndView = new ModelAndView("editProfile");
        //get info of the current logged in user
        final User user = (User) authentication.getPrincipal();
        modelAndView.addObject("fullName", user.getFirstname() + " " + user.getLastname());
        modelAndView.addObject("email", user.getEmail());
        //Get input from Textfields
        final CreateUserCmd userCmd = new CreateUserCmd();
        userCmd.setFirstname(user.getFirstname());
        userCmd.setLastname(user.getLastname());
        modelAndView.addObject("createUserCmd", userCmd);
        //get All Location and and convert iterable list to an array list of Locations
        final ArrayList<Location> locNames = new ArrayList<>();
        locationService.getAllLocations().forEach(locNames::add);
        modelAndView.addObject("locationNames", locNames);


        return modelAndView;
    }


    /**
     * Edit user profile.
     * @param user User to edit.
     * @param createUserCmd user properties.
     * @param file User avatar.
     * @return ModelAndView.
     */
    @PostMapping("/editProfile")
    public ModelAndView editProfile(@AuthenticationPrincipal final User user, //NOPMD
                                    @ModelAttribute("createUserCmd") final CreateUserCmd createUserCmd,
                                    final @RequestParam(value = "file", required = false) MultipartFile file) {
        //check if there is an uploaded pic and change all user info if there is any
        if (file != null && !file.isEmpty()) {
            dbFileStorageService.store(file);
            user.setPicture(file.getOriginalFilename());
        }

        user.setFirstname(createUserCmd.getFirstname());
        user.setLastname(createUserCmd.getLastname());
        if (createUserCmd.getLocation() != null) {
            final ArrayList<Location> locNames = new ArrayList<>();
            locationService.getAllLocations().forEach(locNames::add);
            for (final Location location : locNames) {
                if (location.getName().equals(createUserCmd.getLocation())) {
                    user.setLocation(location);
                }
            }

        }

        userService.updateUser(user);
        return new ModelAndView("redirect:/profile");
    }
}


