package gpse.team52.domain;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Room where a meeting is held.
 */
@NoArgsConstructor
@Entity
public class MeetingRoom implements Serializable {
    private static final long serialVersionUID = -6856942505616017100L;
    /**
     * The meeting.
     */
    @Getter
    @Setter
    @EmbeddedId
    @MapsId("meeting_id")
    @JoinColumn(name = "meeting_id")
    @ManyToOne
    private Meeting meeting;

    /**
     * The room.
     */
    @Getter
    @Setter
    @EmbeddedId
    @MapsId("room_id")
    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;

    /**
     * The number of participants in the given room.
     */
    @Getter
    @Column
    private int participants;

    /**
     * Create a new MeetingRoom relation.
     * @param meeting The meeting.
     * @param room The room.
     * @param participants The number of participants in the given room.
     */
    public MeetingRoom(final Meeting meeting, final Room room, final int participants) {
        this.meeting = meeting;
        this.room = room;
        this.participants = participants;
    }
}
