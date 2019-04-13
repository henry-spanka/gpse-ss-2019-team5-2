package gpse.team52.seeder;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        try {
            userService.loadUserByUsername("admin");
        } catch (UsernameNotFoundException ex) {
            userService.createUser("admin",
            "{bcrypt}$2a$10$WoG5Z4YN9Z37EWyNCkltyeFr6PtrSXSLMeFWOeDUwcanht5CIJgPa",
            "Demo", "Admin", "ROLE_USER");
        }
    }
}
