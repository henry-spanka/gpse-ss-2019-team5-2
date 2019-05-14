package gpse.team52.repository;

import gpse.team52.domain.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Location repository interface.
 */
public interface LocationRepository extends CrudRepository<Location, UUID> {
}
