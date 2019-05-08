package gpse.team52.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Room {

    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID roomId;

    @Getter
    @Column(nullable = false)
    private int seats;

    @Getter
    @Column(nullable = false)
    private int expandableSeats;

    @Getter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @ManyToOne(targetEntity = Location.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "locationId")
    private Location location;

    protected Room() {
    }

    public Room(int seats, int expandableSeats, String email, Location location) {
        this.seats = seats;
        this.expandableSeats = expandableSeats;
        this.email = email;
        this.location = location;
    }
}
