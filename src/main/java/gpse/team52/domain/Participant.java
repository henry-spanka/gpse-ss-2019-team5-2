package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Participant Enitity.
 */
@Entity
public class Participant {

    /**
     * Unique Id for every participant.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false,
    columnDefinition = "BINARY(16)", unique = true)
    private UUID participantId;

    /**
     * If participant is intern user, this column is filled.
     */
    @Getter
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "userId")
    private User user;

    /**
     * Email of extern participants.
     */
    @Column(nullable = true)
    private String email;

    /**
     * First name of extern participants.
     */
    @Column(nullable = true)
    private String firstName;

    /**
     * Last name of extern participants.
     */
    @Column(nullable = true)
    private String lastName;

    /**
     * The meeting that is participated.
     */
    @Getter
    @Setter
    @ManyToOne(targetEntity = Meeting.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "meetingId")
    private Meeting meeting;


    public Participant(@NotNull final User user) {
        this.user = user;
    }

    public Participant(@NotNull final String email, @NotNull final String firstName,
                       @NotNull final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        if (firstName == null) {
            return user.getFirstname();
        }

        return firstName;
    }

    public  String getLastName() {
        if (lastName == null) {
            return user.getLastname();
        }

        return lastName;
    }

    public String getEmail() {
        if (email == null) {
            return user.getEmail();
        }

        return email;
    }

    public boolean isUser() {
        return user != null;
    }
}
