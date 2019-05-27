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
    List<Room> findByLocationAndSeatsGreaterThanEqual(Location location, int seats); // geht das überhaupt?

    @Query("SELECT DISTINCT r FROM Room r WHERE r.location.id = :locId "
    + "AND r.seats >= :seats ORDER BY r.seats, r.expandableSeats ASC")
    List<Room> findByLocationAndSeatsGreaterThanEqual(@Param("locId") UUID location, @Param("seats") int seats);

    //TODO add start, end and date search
    Iterable<Room> findByEquipmentContaining(List<Equipment> equipment); // so??

    Iterable<Room> findByExpandableSeatsGreaterThanEqual(int expandableSeats); // und macht das hier Sinn,
    // oder sucht man eh erstmal nach Sitzplätzen und schaut sich dann den entsprechenden Raum genauer an
    // und vergleicht erwieterbare Sitzplätze?
}
/*
https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html
https://www.jeejava.com/spring-boot-data-jpa-left-right-inner-and-cross-join-examples/
 */
