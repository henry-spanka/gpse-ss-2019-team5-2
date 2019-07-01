package gpse.team52.form;

import javax.validation.constraints.NotBlank;

import gpse.team52.contract.HasPassword;
import gpse.team52.validator.PasswordMatches;
import gpse.team52.validator.ValidPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * New Password Form in order to read the password the user want to have.
 */
@PasswordMatches
public class NewPasswordForm implements HasPassword {

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
