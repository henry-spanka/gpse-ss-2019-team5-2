package gpse.team52;

import lombok.Getter;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int roomID;

    @Column(unique = true, nullable = false)
    private String roomEmail;

    @Column(nullable = false)
    private String roomName;

    @Column(nullable = false)
    private int seats;

    @Column
    private int extraSeats;


    //TODO needs to be changed to any list or class or whatever
    @Column
    private String equipment;

    //TODO add: layout/lageplan, location (and class location), belegungsplan
    //@Column
    //private Location location;

    //TODO change constructor, add variables
    public Room(int id, String email, String name, int seats, int extraSeats, String equipment) {
        this.roomName = name;
        this.seats = seats;
        this.equipment =  equipment;
        this.roomID = id;
        this.roomEmail = email;
        this.extraSeats = extraSeats;
    }

    public String getRoomName() {
        return roomName;
    }
    public String getEquipment() {
        return equipment;
    }
    public int getSeats() {
        return seats;
    }
    public int getRoomID() { return roomID; }
    public int getExtraSeats() {
        return extraSeats;
    }
    public String getRoomEmail() {
        return roomEmail;
    }

}
