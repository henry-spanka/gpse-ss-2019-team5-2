package gpse.team52.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

//TODO: - set attributes correct for database
// - set little star if room is a favourite
// - use database to access data in controller
// - roomdetails page
// - testdata (room, equipment) for database
// - unit tests for functions submit, cancel, back
// - submit data for real at last submit step
// - add equipment
// - merge into develop and fix any f problems
// - (get back from last step and detailed information with rooms still selected)


/**
 * Room Entity.
 */
@Entity
public class Room {

    /**
     * Unique Id for each room.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)")
    private UUID roomID;

    /**
     * Number of fix seats in a room.
     */
    @Getter
    @Column(nullable = false)
    private int seats;

    /**
     * Number of optional seats for a room.
     */
    @Getter
    @Column(nullable = false)
    private int expandableSeats;

    /**
     * Email address of a room.
     */
    @Getter
    @Column(unique = true, nullable = false)
    private String roomEmail;

    /**
     * Name of a room.
     */
    @Getter
    @Column(nullable = false) //TODO nullable should be false!
    private String roomName;

    /**
     * Location of the room.
     */
    @Getter
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "locationId")
    private Location location;


    //TODO schauen ob layout klappt
    @Column(name = "layout")
    private byte[] layout;

    @Getter
    @ManyToMany(targetEntity = Equipment.class)
    @JoinColumn(nullable = false, name = "equipmentId")
    private List<Equipment> equipment = new ArrayList<>();

    protected Room() {
    }

    /**
     * Constructor for a room.
     * @param seats Define number of seats
     * @param expandableSeats Define number of optional seats
     * @param email Email address of the room
     * @param location Location of the room
     * @param roomName
     */
    public Room(final int seats, final int expandableSeats,
                final String email, final Location location, String roomName) {
        this.seats = seats;
        this.expandableSeats = expandableSeats;
        this.roomEmail = email;
        this.location = location;
        this.roomName = roomName;
    }

    public void addEquipment(Equipment equipment) {
        this.equipment.add(equipment);
    }

    public void addEquipment(List<Equipment> equipment) {
        this.equipment.addAll(equipment);
    }

    public void addEquipment(Equipment... equipments) {
        addEquipment(Arrays.asList(equipments));
    }
}
