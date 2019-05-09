package gpse.team52.repository;

import gpse.team52.domain.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RoomRepository extends CrudRepository<Room, UUID> {
}
