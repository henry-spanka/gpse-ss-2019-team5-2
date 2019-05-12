package gpse.team52.domain;

import lombok.Getter;
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
