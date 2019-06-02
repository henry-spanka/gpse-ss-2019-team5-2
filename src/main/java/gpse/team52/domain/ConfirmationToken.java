package gpse.team52.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 * ConfirmationToken Entity.
 */
@Entity
@NoArgsConstructor
public class ConfirmationToken {
    @Getter
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID token;

    @Getter
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "userId")
    private User user;

    @Getter
    @ManyToOne(targetEntity = Meeting.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "meetingId")
    private Meeting meeting;

    /**
     * Generate a new confirmation token for a user.
     * @param user The user to generate the token for.
     */
    public ConfirmationToken(final User user) {
        this.user = user;
    }

    /**
     * Generate a new confirmation token for a user.
     * @param user The user to generate the token for.
     * @param token The token to use.
     */
    public ConfirmationToken(final User user, final UUID token) {
        this.user = user;
        this.token = token;
    }

    /**
     * Generate a new confirmation token for a user and a specific meeting.
     * @param user The user to generate the token for.
     * @param token The token to use.
     * @param meeting The meeting to generate the token for.
     */
    public ConfirmationToken(final User user, final UUID token, final Meeting meeting) {
        this.user = user;
        this.token = token;
        this.meeting = meeting;
    }
}
