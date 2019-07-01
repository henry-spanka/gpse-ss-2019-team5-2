package gpse.team52.domain;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Participant Enitity.
 */
@NoArgsConstructor
@Entity
public class Participant { //NOPMD

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
    @Setter
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "userId")
    private User user;

    /**
     * Email of extern participants.
     */
    @Setter
    @Column(nullable = true)
    private String email;

    /**
     * First name of extern participants.
     */
    @Setter
    @Column(nullable = true)
    private String firstName;

    /**
     * Last name of extern participants.
     */
    @Setter
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

    /**
     * Notify the participant via email.
     */
    @Getter
    @Setter
    @Column(nullable = false)
    private boolean notifiable = false;


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
     * @return String.
     */
    public String getFirstName() {
        if (firstName == null) {
            return user.getFirstname();
        }

        return firstName;
    }

    /**
     * Custom getter for last name.
     * @return String.
     */
    public  String getLastName() {
        if (lastName == null) {
            return user.getLastname();
        }

        return lastName;
    }

    /**
     * Custom getter for email.
     * @return String.
     */
    public String getEmail() {
        if (email == null) {
            return user.getEmail();
        }

        return email;
    }

    public String getFullName() {
        return getFirstName() + ' ' + getLastName();
    }

    public boolean isUser() {
        return user != null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Participant participant = (Participant) obj;

        return Objects.equals(getFirstName(), participant.getFirstName())
        && Objects.equals(getLastName(), participant.getLastName())
        && Objects.equals(getEmail(), participant.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, firstName, lastName);
    }
}
