package gpse.team52.contract;

import gpse.team52.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * User Service Contract.
 */
public interface UserService extends UserDetailsService {

    User createUser(String username, String password, String firstname, String lastname, String... roles);
}
