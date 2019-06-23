package gpse.team52.repository;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User Repository.
 */
public interface UserRepository extends CrudRepository<User, UUID> {
    /**
     * Finds a given user by their username.
     * @param username The username to search for.
     * @return Returns the User (or null) wrapped by the optional container.
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a given user by their email.
     * @param email The email to search for.
     * @return Returns the User (or null) wrapped by the optional container.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by iCal token.
     * @param token iCal token.
     * @return Returns the user (or null) wrapped by the optional container.
     */
    Optional<User> findByICalToken(UUID token);
}
