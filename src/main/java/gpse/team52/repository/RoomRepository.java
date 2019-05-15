package gpse.team52.repository;

import java.util.UUID;

import gpse.team52.domain.Room;
import org.springframework.data.repository.CrudRepository;

/**
 * Room repository interface.
 */
public interface RoomRepository extends CrudRepository<Room, UUID> {
}
