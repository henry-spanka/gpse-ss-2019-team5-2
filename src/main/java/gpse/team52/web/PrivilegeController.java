package gpse.team52.web;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import gpse.team52.contract.PrivilegeService;
import gpse.team52.contract.RoleService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.Role;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Privilege controller.
 */
@Controller
@RequestMapping("/privileges")
public class PrivilegeController {
    private RoleService roleService;
    private PrivilegeService privilegeService;
    private UserService userService;

    /**
     * Initialize privilege controller.
     *
     * @param roleService      The role service.
     * @param privilegeService The privilege service.
     * @param userService      The user service.
     */
    @Autowired
    public PrivilegeController(final RoleService roleService,
                               final PrivilegeService privilegeService,
                               final UserService userService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.userService = userService;
    }

    /**
     * Get all privileges.
     *
     * @return all privileges.
     */
    @GetMapping
    public ModelAndView getAllPrivileges() {
        final ModelAndView modelAndView = new ModelAndView("privileges");
        modelAndView.addObject("roles", roleService.getAll());
        modelAndView.addObject("privileges", privilegeService.getAll());
        return modelAndView;
    }

    /**
     * Get privileges for role.
     *
     * @param roleName The role.
     * @return privileges for role.
     */
    @GetMapping("/role/{roleName}")
    public ModelAndView getPrivilegesForRole(final @PathVariable("roleName") String roleName) {
        final ModelAndView modelAndView = new ModelAndView("privileges-role");
        modelAndView.addObject("role", roleService.getByName(roleName).orElseThrow());
        modelAndView.addObject("privileges", privilegeService.getAll());
        return modelAndView;
    }

    /**
     * Update privileges for role.
     *
     * @param roleName   The role.
     * @param privileges The privileges.
     * @return redirect to the privileges pages.
     */
    @PostMapping(value = "/role")
    public ModelAndView updatePrivilegesForRole(final @RequestParam String roleName,
                                                final @RequestParam Set<String> privileges) {
        final Role role = roleService.getByName(roleName).orElseThrow();
        role.setPrivileges(privileges.stream().map(name ->
        privilegeService.getByName(name).orElseThrow(EntityNotFoundException::new)).collect(Collectors.toSet()));
        roleService.update(role);
        return new ModelAndView("redirect:/privileges");
    }

    /**
     * Get privileges for user.
     *
     * @param username Tne user.
     * @return view.
     */
    @GetMapping("/user")
    public ModelAndView getPrivilegesForUser(final @RequestParam("username") String username) {
        final ModelAndView modelAndView = new ModelAndView("privileges-user");
        modelAndView.addObject("user", userService.loadUserByUsername(username));
        modelAndView.addObject("roles", roleService.getAll());
        modelAndView.addObject("privileges", privilegeService.getAll());
        return modelAndView;
    }

    /**
     * Update privileges for user.
     *
     * @param username   The user.
     * @param privileges The privileges.
     * @return redirect to the privileges pages.
     */
    @PostMapping(value = "/user")
    public ModelAndView updatePrivilegesForUser(final @RequestParam String username,
                                                final @RequestParam Set<String> privileges) {
        final User user = userService.loadUserByUsername(username);
        user.setPrivileges(privileges.stream().map(name ->
        privilegeService.getByName(name).orElseThrow(EntityNotFoundException::new)).collect(Collectors.toSet()));
        userService.updateUser(user);
        return new ModelAndView("redirect:/privileges");
    }
}
