package gpse.team52.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class Participant {

    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)", unique = true)
    private UUID participantId;

    @Getter
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "userId")
    private User user;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Getter
    @Setter
    @ManyToOne(targetEntity = Meeting.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "meetingId")
    private Meeting meeting;


    public Participant(@NotNull User user) {
        this.user = user;
    }

    public Participant(@NotNull String email, @NotNull  String firstName, @NotNull String lastName) {
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
