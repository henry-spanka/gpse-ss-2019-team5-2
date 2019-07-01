package gpse.team52.contract;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Privilege;

/**
 * Privileges service.
 */
public interface PrivilegeService {
    Iterable<Privilege> getAll();

    Optional<Privilege> get(UUID privilegeId);

    Optional<Privilege> getByName(String name);

    Privilege create(Privilege privilege);

    Privilege update(Privilege privilege);

    void delete(Privilege privilege);
}
