package gpse.team52.form;

import gpse.team52.validator.PasswordMatches;
import gpse.team52.validator.ValidEmail;
import gpse.team52.validator.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Password Reset Mail Form to read the mail of the account the user wants to reset the password of.
 */

public class PasswordResetMailForm {

    @ValidEmail
    @NotBlank
    @Getter
    @Setter
    private String email;

}
