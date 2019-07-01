package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.RoleService;
import gpse.team52.contract.UserService;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Initialize the default user in the database.
 */
@Service
public class InitializeDefaultUser {

    private static final String DEFAULT_PASSWORD = "password";

    private final RoleService roleService;
    private final UserService userService;

    private final InitializeDefaultRoles initializeDefaultRoles;

    /**
     * InitializeDefaultUser Constructor.
     * @param roleService RoleService.
     * @param userService UserService.
     * @param initializeDefaultRoles InitializeDefaultRoles.
     */
    @Autowired
    public InitializeDefaultUser(final RoleService roleService,
                                 final UserService userService,
                                 final InitializeDefaultRoles initializeDefaultRoles) {
        this.roleService = roleService;
        this.userService = userService;
        this.initializeDefaultRoles = initializeDefaultRoles;
    }

    /**
     * Initializes the default admin user if it doesn't exist already.
     */
    @PostConstruct
    public void init() {
        final UserRegistrationForm form = new UserRegistrationForm();
        form.setFirstName("Demo");
        form.setLastName("Admin");
        form.setEmail("demo.admin@example.org");
        form.setUsername("admin");
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        try {
            userService.createUser(form, true, roleService.getByName("ROLE_ADMIN").orElseThrow());
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
        }


        form.setFirstName("Markus");
        form.setLastName("G.");
        form.setEmail("markus.gaffke124@capgeminitest.org");
        form.setUsername("mgaffke");
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        try {
            userService.createUser(form, true, roleService.getByName("ROLE_ADMIN").orElseThrow());
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            //
        }

        form.setFirstName("Markus");
        form.setLastName("C.");
        form.setEmail("markus.c124@capgeminitest.org");
        form.setUsername("markusc");
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        try {
            userService.createUser(form, true, roleService.getByName("ROLE_ADMIN").orElseThrow());
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            //
        }

        form.setFirstName("Marie-Sophie");
        form.setLastName("B.");
        form.setEmail("marie12412421@capgeminitest.org");
        form.setUsername("mborat");
        form.setPassword(DEFAULT_PASSWORD);
        form.setPasswordConfirm(DEFAULT_PASSWORD);

        try {
            userService.createUser(form, true, roleService.getByName("ROLE_ADMIN").orElseThrow());
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            //
        }

    }
}
