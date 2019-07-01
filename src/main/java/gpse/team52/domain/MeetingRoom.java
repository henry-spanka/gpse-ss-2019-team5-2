package gpse.team52.domain;


import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

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
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)")
    private UUID meetingRoomId;

    @Getter
    @Setter
    @JoinColumn(name = "meeting_id")
    @ManyToOne
    private Meeting meeting;

    /**
     * The room.
     */
    @Getter
    @Setter
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

    public MeetingRoom(final Room room, final int participants) {
        this.room = room;
        this.participants = participants;
    }



}
