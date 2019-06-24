package gpse.team52.form;

import javax.validation.constraints.NotBlank;

import gpse.team52.validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

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
