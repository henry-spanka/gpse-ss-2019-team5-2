package gpse.team52.contract;

import gpse.team52.domain.Privilege;

import java.util.Optional;
import java.util.UUID;

public interface PrivilegeService {
    Iterable<Privilege> getAll();

    Optional<Privilege> get(UUID privilegeId);

    Privilege create(Privilege privilege);

    Privilege update(Privilege privilege);

    void delete(Privilege privilege);
}
