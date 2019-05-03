package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Meeting {
    /**
     * Unique id for each individual meeting.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Date and time for the beginning of the meeting.
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime startAt;

    /**
     * Date and time for the ending of the meeting.
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private LocalDateTime endAt;

    /**
     * Title of the meeting.
     */
    @Getter
    @Column(nullable = false)
    private String title;

    @Getter
    @Setter
    @Column
    private String description;

    /**
     * The number of participants for the meeting.
     */
    @Getter
    @Column
    private int participantsNumber;

    protected Meeting() {
    }

    public Meeting(final String title, final int participantsNumber) {
        this.title = title;
        this.participantsNumber = participantsNumber;
    }

    public int getDuration() {
        return (int)(Duration.between(startAt, endAt).getSeconds() / 60);
    }

}
