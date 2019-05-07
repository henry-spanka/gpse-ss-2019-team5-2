package gpse.team52;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomId", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int roomID;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String roomEmail;

    @Getter
    @Setter
    @Column(nullable = false)
    private String roomName;

    @Getter
    @Setter
    @Column(nullable = false)
    private int seats;

    @Getter
    @Setter
    @Column
    private int extraSeats;

    @Getter
    @Setter
    @Column
    private List<Equipment> equipment;

    @Getter
    @Setter
    @Column(nullable = false)
    private String location;

    //TODO schauen ob layout klappt
    @Column(name="layout")
    private byte[] layout;

    //TODO change constructor, add variables
    public Room() {

    }

    public byte[] getLayout() {
        return layout;
    }

}
