package gpse.team52;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

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
    //@Column
    //private List<Equipment> equipment;

    @Column(nullable = false)
    private String location;

    //TODO schauen ob layout klappt
    @Column(name="layout")
    private byte[] layout;

    //TODO change constructor, add variables
    public Room(int id, String email, String name, int seats, int extraSeats, String location) {
        this.roomName = name;
        this.seats = seats;
        this.roomID = id;
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
    public int getRoomID() { return roomID; }
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

}
