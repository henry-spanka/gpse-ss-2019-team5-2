package gpse.team52.repository;

import java.util.UUID;

import gpse.team52.domain.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

/**
 * ConfirmationToken Repository to save validation tokens to the database.
 */
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, UUID> {

}
