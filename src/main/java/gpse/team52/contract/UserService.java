package gpse.team52.contract;

import java.util.UUID;

import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.EmailNotFoundException;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * User Service Contract.
 */
public interface UserService extends UserDetailsService {
    /**
     * Create a new user from a registration form. The user is disabled by default.
     *
     * @param form  The registration form which containers the user's details.
     * @param roles The roles to assign to the user.
     * @return The persisted user in the database.
     */
    User createUser(UserRegistrationForm form, String... roles) throws UsernameExistsException, EmailExistsException;

    /**
     * Create a new user from a registration form and sends a verification email.
     *
     * @param form    The registration form which containers the user's details.
     * @param enabled Whether the user is enabled (email verified).
     * @param roles   The roles to assign to the user.
     * @return The persisted user in the database.
     * @throws UsernameExistsException Thrown if the username already exists in the database.
     * @throws EmailExistsException    Thrown if the email already exists in the database.
     */
    User createUser(UserRegistrationForm form, boolean enabled, String... roles) throws UsernameExistsException,
    EmailExistsException;

    @Override
    User loadUserByUsername(String s) throws UsernameNotFoundException;

    /**
     * Find a user by their email.
     *
     * @param email The email to search for.
     * @return The User.
     * @throws EmailNotFoundException Thrown if the user cannot be found.
     */
    User loadUserByEmail(String email) throws EmailNotFoundException;

    /**
     * Send a verification email to the user's email address.
     *
     * @param user The User to verify.
     */
    void sendVerificationEmail(User user);

    /**
     * Validate a users account by the token sent to their email address.
     * This function will also remove the token from the database.
     *
     * @param token The token sent to their email address.
     * @return The validated (enabled) User.
     * @throws InvalidConfirmationTokenException Thrown if the token has an invalid format or is expired.
     */
    User validateUserFromToken(UUID token) throws InvalidConfirmationTokenException;
}
