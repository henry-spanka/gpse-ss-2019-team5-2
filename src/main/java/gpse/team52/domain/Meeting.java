package gpse.team52.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Meeting Entity.
 */
@Entity
@NoArgsConstructor
public class Meeting {
    /**
     * Unique id for each individual meeting.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)")
    private UUID meetingId;

    /**
     * Owner of the meeting.
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User owner;

    /**
     * List of participants for the meeting.
     */
    @Getter
    @OneToMany(mappedBy = "meeting", cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();

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

    /**
     * Room where the meeting takes place.
     */
    @Getter
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "roomId")
    private Room room;


    public Meeting(final String title, final int participantsNumber,
                   final Room room) {
        this.title = title;
        this.participantsNumber = participantsNumber;
        this.room = room;
    }

    @SuppressWarnings("checkstyle:magicnumber")
    public int getDuration() {
        return (int) (Duration.between(startAt, endAt).getSeconds() / 60);
    }

    public void addParticipant(final Participant participant) {
        participants.add(participant);
        participant.setMeeting(this);
        return;
    }
}
