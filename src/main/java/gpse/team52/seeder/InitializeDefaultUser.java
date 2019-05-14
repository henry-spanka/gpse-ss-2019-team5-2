package gpse.team52.seeder;

import javax.annotation.PostConstruct;

import gpse.team52.contract.UserService;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes the Default User in the database.
 */
@Service
public class InitializeDefaultUser {

    private static final String DEFAULT_PASSWORD = "password";

    private final EquipmentService equipmentService;

    @Autowired
    public InitializeDefaultUser(final UserService userService) {
        this.userService = userService;
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
            userService.createUser(form, true, "ROLE_ADMIN");
        } catch (UsernameExistsException | EmailExistsException e) { //NOPMD
            // Not an issue as we only need to create the admin user if it doesn't exist already.
        }
    }
}
