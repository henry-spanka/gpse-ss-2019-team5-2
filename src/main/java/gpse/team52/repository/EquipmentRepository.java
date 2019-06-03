package gpse.team52.repository;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Equipment;
import org.springframework.data.repository.CrudRepository;

/**
 * Equipment repository interface.
 */
public interface EquipmentRepository extends CrudRepository<Equipment, UUID> {
    Optional<Equipment> findByEquipmentName(String name);
}
