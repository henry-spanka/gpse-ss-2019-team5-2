package gpse.team52.contract;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Role;

/**
 * Role service.
 */
public interface RoleService {
    Iterable<Role> getAll();

    Optional<Role> get(UUID roleId);

    Optional<Role> getByName(String name);

    Role create(Role role);

    Role update(Role role);

    void delete(Role role);
}
