package gpse.team52.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

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


@Entity
public class Room {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID roomID;

    @Column(unique = true, nullable = false)
    private String roomEmail;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int seats;

    @Column
    private int extraSeats;


    //TODO needs to be changed to any list or class or whatever
    //@Column
    //private List<Equipment> equipment;

    @Column(nullable = false)
    private String location;

    //TODO schauen ob layout klappt
    @Column(name="layout")
    private byte[] layout;

    //TODO change constructor, add variables
    public Room(String email, String name, int seats, int extraSeats, String location) {
        this.roomName = name;
        this.seats = seats;
        this.roomEmail = email;
        this.extraSeats = extraSeats;
        this.location = location;
        this.layout = layout;
    }

    public String getRoomName() {
        return roomName;
    }
    public int getSeats() {
        return seats;
    }
    public UUID getRoomID() { return roomID; }
    public int getExtraSeats() {
        return extraSeats;
    }
    public String getRoomEmail() {
        return roomEmail;
    }
    public String getLocation() {
        return location;
    }
    public byte[] getLayout() {
        return layout;
    }

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * Room Entity.
 */
@Entity
public class Room {

    /**
     * Unique Id for each romm.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)")
    private UUID roomId;

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
    private String email;

    /**
     * Location of the room.
     */
    @Getter
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "locationId")
    private Location location;

    protected Room() {
    }

    public Room(final int seats, final int expandableSeats,
                final String email, final Location location) {
        this.seats = seats;
        this.expandableSeats = expandableSeats;
        this.email = email;
        this.location = location;
    }
}
