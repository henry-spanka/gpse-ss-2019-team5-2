package gpse.team52.contract;

import gpse.team52.domain.Role;

import java.util.Optional;
import java.util.UUID;

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
