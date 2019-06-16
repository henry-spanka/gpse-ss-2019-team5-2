package gpse.team52.repository;

import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Room repository interface.
 */
public interface RoomRepository extends CrudRepository<Room, UUID> {

    @Query("SELECT DISTINCT r FROM Room r WHERE r.location.id = :locId "
    + "AND (r.seats + r.expandableSeats)>= :seats ORDER BY r.seats, r.expandableSeats ASC")
    List<Room> findByLocationAndSeatsGreaterThanEqual(@Param("locId") UUID location, @Param("seats") int seats);

}
