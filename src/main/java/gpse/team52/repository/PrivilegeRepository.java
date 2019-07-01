package gpse.team52.repository;

import gpse.team52.domain.Privilege;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PrivilegeRepository extends CrudRepository<Privilege, UUID> {
}
