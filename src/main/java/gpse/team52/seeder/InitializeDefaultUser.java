package gpse.team52.seeder;

import gpse.team52.contract.UserService;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes the Default User in the database.
 */
@Service
public class InitializeDefaultUser implements InitializingBean {

    private final UserService userService;

    @Autowired
    public InitializeDefaultUser(final UserService userService) {
        this.userService = userService;
    }


    @Override
    public void afterPropertiesSet() {
        UserRegistrationForm form = new UserRegistrationForm();
        form.setFirstName("Demo");
        form.setLastName("Admin");
        form.setEmail("demo.admin@example.org");
        form.setUsername("admin");
        form.setPassword("password");
        form.setPasswordConfirm("password");

        try {
            userService.createUser(form, "ROLE_ADMIN");
        } catch (UsernameExistsException | EmailExistsException e) {

        }
    }
}
