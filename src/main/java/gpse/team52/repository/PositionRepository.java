package gpse.team52.repository;

import gpse.team52.domain.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PositionRepository extends CrudRepository<Position, UUID>{
}
