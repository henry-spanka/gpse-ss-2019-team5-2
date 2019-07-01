package gpse.team52.web;

import gpse.team52.contract.PrivilegeService;
import gpse.team52.contract.RoleService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Privilege;
import gpse.team52.domain.Role;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping("/rightAdministration")
public class PrivilegeController {
    private RoleService roleService;
    private PrivilegeService privilegeService;
    private UserService userService;

    @Autowired
    public PrivilegeController(RoleService roleService,
                               PrivilegeService privilegeService,
                               UserService userService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.userService = userService;
    }

    /**
     * @return Page with available rooms to choose from
     */
    @GetMapping
    public ModelAndView rights() {
        final ModelAndView modelAndView = new ModelAndView("rightAdministration");
        modelAndView.addObject("positionList", roleService.getAll());
        modelAndView.addObject("rightList", privilegeService.getAll());
        return modelAndView;
    }

    @GetMapping("{username}")
    public ModelAndView rightsForUser(@PathVariable("username") String username) {
        final ModelAndView modelAndView = new ModelAndView("rightAdministration");
        modelAndView.addObject("positionList", roleService.getAll());
        modelAndView.addObject("rightList", privilegeService.getAll());
        return modelAndView;
    }

    @GetMapping("/positions/{username}")
    public Collection<Role> getPositionsForUser(@PathVariable("username") String username) {
        return null;
    }

    @GetMapping("/test/{username}")
    public User test(@PathVariable("username") String username) {
        return userService.loadUserByUsername(username);
    }

    @GetMapping("/rights/{username}")
    public Collection<Privilege> getRightsForUser(@PathVariable("username") String username) {
        return null;
    }

    @PostMapping("/{right}")
    public Privilege updateRight(@PathVariable("right") Privilege privilege) {
        return privilege;
    }
}
