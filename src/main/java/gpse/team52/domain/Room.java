package gpse.team52.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

//TODO: - set attributes correct for database
// - set little star if room is a favourite
// - use database to access data in controller
// - roomdetails page
// - testdata (room, equipment) for database
// - unit tests for functions submit, cancel, back
// - submit data for real at last submit step
// - add equipment
// - Belegungsplan für Räume
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
    @Setter
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

    @Getter
    @Column(unique = true, nullable = false)
    private String layoutName;
    //TODO needs to be changed to any list or class or whatever

    @Getter
    @ManyToMany(targetEntity = Equipment.class)
    @JoinColumn(nullable = false, name = "equipment")
    private List<Equipment> equipment = new ArrayList<>();

    protected Room() {
    }

    /**
     * Constructor for a room.
     *
     * @param seats           Define number of seats
     * @param expandableSeats Define number of optional seats
     * @param roomName        Name of the Room
     * @param email           Email address of the room
     * @param location        Location of the room
     * @param layoutName      Name of the layout of a room
     */
    public Room(final int seats, final int expandableSeats, final String roomName,
                final String email, final Location location, final String layoutName) {
        this.seats = seats;
        this.expandableSeats = expandableSeats;
        this.roomEmail = email;
        this.location = location;
        this.layoutName = layoutName;
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

    /*TODO: passt der pfad?
    TODO: pfad zu ressources passend einfügen
    TODO: wie wird es in die html geladen?
    TODO: im controller anpassen das er das da tut
     */
    public String getLayoutPath(){
        return "static/pictures/layout" + layoutName + ".png";
    }
}
