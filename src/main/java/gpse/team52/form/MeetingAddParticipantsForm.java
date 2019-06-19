package gpse.team52.form;

import java.util.List;

import javax.validation.constraints.Size;

import gpse.team52.validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

/**
 * Form to add new participants to a meeting.
 */
public class MeetingAddParticipantsForm {
    @Getter
    @Setter
    private List<String> participants;

    @Getter
    @Setter
    @Size(max = 100)
    private String firstName;

    @Getter
    @Setter
    @Size(max = 100)
    private String lastName;

    @Getter
    @Setter
    @ValidEmail(nullable = true, empty = true)
    private String email;

    /**
     * Checks whether an attempt has been made to fill external fields.
     * @return True or false to indicate an attempt.
     */
    public boolean externalFilled() {
        return firstName != null && !firstName.isEmpty()
            || lastName != null && !lastName.isEmpty()
            || email != null && !email.isEmpty();
    }

    public boolean externalComplete() {
        return externalFilled() && !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty();
    }
}
