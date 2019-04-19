package gpse.team52.contract;

import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.EmailNotFoundException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * User Service Contract.
 */
public interface UserService extends UserDetailsService {
    /**
     * Create a new user from a registration form.
     * @param form The registration form which containers the user's details.
     * @param roles The roles to assign to the user.
     * @return The persisted user in the database.
     */
    User createUser(UserRegistrationForm form, String... roles) throws UsernameExistsException, EmailExistsException;

    /**
     * Find a user by their email.
     * @param email The email to search for.
     * @return The User.
     * @throws EmailNotFoundException Thrown if the user cannot be found.
     */
    UserDetails loadUserByEmail(String email) throws EmailNotFoundException;
}
