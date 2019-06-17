package gpse.team52.repository;

import gpse.team52.domain.Position;
import gpse.team52.domain.Right;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RightRepository extends CrudRepository<Right, UUID> {
}
