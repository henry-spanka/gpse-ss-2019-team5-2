package gpse.team52.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Meeting entity
 */
@Entity
@NoArgsConstructor
public class Meeting {
    /**
     * Unique id for each individual meeting.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long meetingId;

    /**
     * Date and time for the beginning of the meeting.
     */
    @Column
    private LocalDateTime startAt;

    /**
     * Date and time for the ending of the meeting.
     */
    @Column
    private LocalDateTime endAt;

    /**
     * Title of the meeting.
     */
    @Column
    private String title;

    /**
     * The number of participants for the meeting.
     */
    @Column
    private Long participantsNumber;

    public Meeting(final String title, final Long participantsNumber) {
        this.title = title;
        this.participantsNumber = participantsNumber;
    }

    public Long getMeetingId() {
        return meetingId;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public String getTitle() {
        return title;
    }

    public Long getParticipantsNumber() {
        return participantsNumber;
    }
}
