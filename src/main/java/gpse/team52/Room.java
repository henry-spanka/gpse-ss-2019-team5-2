package gpse.team52;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room {

    @Id
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int roomID;

    @Column
    private String roomName;

    @Column
    private int seats;

    @Column
    private int extraSeats;

    @Column(unique = true, nullable = false)
    @Getter
    private String roomEmail;

    //TODO needs to be changed to any list or class or whatever
    @Column
    private String equipment;

    //TODO add: layout/lageplan, location (and class location), belegungsplan
    //@Column
    //private Location location;

    public Room(String name, int seats, String equipment, int id) {
        this.roomName = name;
        this.seats = seats;
        this.equipment =  equipment;
        this.roomID = id;
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

}
