package gpse.team52.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Participant Enitity.
 */
@NoArgsConstructor
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

    /**
     * Constructor for a participant.
     * @param email Email of the participant
     * @param firstName First name of the participant
     * @param lastName Last name of the participant
     */
    public Participant(@NotNull final String email, @NotNull final String firstName,
                       @NotNull final String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Custom getter for first name.
     * @return
     */
    public String getFirstName() {
        if (firstName == null) {
            return user.getFirstname();
        }

        return firstName;
    }

    /**
     * Custom getter for last name.
     * @return
     */
    public  String getLastName() {
        if (lastName == null) {
            return user.getLastname();
        }

        return lastName;
    }

    /**
     * Custom getter for email.
     * @return
     */
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
