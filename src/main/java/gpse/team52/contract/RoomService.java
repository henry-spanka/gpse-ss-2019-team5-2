package gpse.team52.contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Room;

/**
 * Room service.
 */
public interface RoomService {

    Room createRoom(int seats, int expandableSeats, String email, Location location, String roomName,
                    String layoutName);

    Room update(Room room);

    Iterable<Room> getAllRooms();

    Optional<Room> getRoom(UUID roomID);

    Iterable<Equipment> getAllEquipment();

    List<Location> findByLocationId(List<UUID> uuidList);

    List<Location> findByLocationIdFromString(List<String> uuidList);
}
