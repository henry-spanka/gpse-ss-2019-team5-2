package gpse.team52.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Meeting {
    /**
     * Unique id for each individual meeting.
     */
    @Id
    @Getter
    @Column
    private Long id;

    /**
     * Date and time for the beginning of the meeting.
     */
    @Getter
    @Column
    private LocalDateTime startAt;

    /**
     * Date and time for the ending of the meeting.
     */
    @Getter
    @Column
    private LocalDateTime endAt;

    /**
     * Title of the meeting.
     */
    @Getter
    @Column
    private String title;

    /**
     * The number of participants for the meeting.
     */
    @Getter
    @Column
    private Long participantsNumber;

    protected Meeting() {
    }

    public Meeting(final String title, final Long participantsNumber) {
        this.title = title;
        this.participantsNumber = participantsNumber;
    }
}
