package gpse.team52.repository;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Location;
import org.springframework.data.repository.CrudRepository;

/**
 * Location repository interface.
 */
public interface LocationRepository extends CrudRepository<Location, UUID> {
    Optional<Location> findByName(String name);
}
