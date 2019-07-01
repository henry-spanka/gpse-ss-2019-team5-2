package gpse.team52.repository;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Privilege;
import org.springframework.data.repository.CrudRepository;

/**
 * Privilege repository.
 */
public interface PrivilegeRepository extends CrudRepository<Privilege, UUID> {
    /**
     * Find privilege by name.
     *
     * @param name The privilege name.
     * @return optional privilege.
     */
    Optional<Privilege> findByName(String name);
}
