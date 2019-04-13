package gpse.team52.repository;

import gpse.team52.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * User Repository.
 */
public interface UserRepository extends CrudRepository<User, String> {
}
