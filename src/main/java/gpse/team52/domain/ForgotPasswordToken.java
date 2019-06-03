package gpse.team52.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

/**
 * ConfirmationToken Entity.
 */
@Entity
@NoArgsConstructor
public class ForgotPasswordToken {
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

    /**
     * Generate a new confirmation token for a user.
     * @param user The user to generate the token for.
     */
    public ForgotPasswordToken(final User user) {
        this.user = user;
    }

    /**
     * Generate a new confirmation token for a user.
     * @param user The user to generate the token for.
     * @param token The token to use.
     */
    public ForgotPasswordToken(final User user, final UUID token) {
        this.user = user;
        this.token = token;
    }
}
