package gpse.team52.form;

import javax.validation.constraints.NotBlank;

import gpse.team52.validator.PasswordMatches;
import gpse.team52.validator.ValidEmail;
import gpse.team52.validator.ValidPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * User Registration Form from which a valid user can be registered.
 */
@PasswordMatches
public class UserRegistrationForm {
    @NotBlank
    @Getter
    @Setter
    private String firstName;

    @NotBlank
    @Getter
    @Setter
    private String lastName;

    @NotBlank
    @Getter
    @Setter
    private String username;

    @NotBlank
    @Getter
    @Setter
    private String location;

    @ValidEmail
    @NotBlank
    @Getter
    @Setter
    private String email;

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
