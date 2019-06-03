package gpse.team52.repository;

import gpse.team52.domain.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * ConfirmationToken Repository to save validation tokens to the database.
 */
public interface ForgotPasswordTokenRepository extends CrudRepository<ForgotPasswordToken, UUID> {

}
