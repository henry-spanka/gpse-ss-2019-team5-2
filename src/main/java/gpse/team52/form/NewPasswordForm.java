package gpse.team52.form;

import gpse.team52.validator.PasswordMatches;
import gpse.team52.validator.ValidEmail;
import gpse.team52.validator.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * New Password Form in order to read the password the user want to have.
 */
@PasswordMatches
public class NewPasswordForm {

    @ValidPassword
    @NotBlank
    @Getter
    @Setter
    private String password;

    @NotBlank
    @Getter
    @Setter
    private String passwordConfirm;
}
