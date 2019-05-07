package gpse.team52.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Meeting Entity.
 */
@Entity
public class Meeting {
    /**
     * Unique id for each individual meeting.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User owner;

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

    /**
     * Description of the meeting.
     */
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

    @SuppressWarnings("checkstyle:magicnumber")
    public int getDuration() {
        return (int) (Duration.between(startAt, endAt).getSeconds() / 60);
    }

}
