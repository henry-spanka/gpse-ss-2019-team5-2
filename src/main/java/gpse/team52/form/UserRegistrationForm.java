package gpse.team52.form;

import gpse.team52.validator.PasswordMatches;
import gpse.team52.validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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

    @ValidEmail
    @NotBlank
    @Getter
    @Setter
    private String email;

    @NotBlank
    @Getter
    @Setter
    private String password;

    @NotBlank
    @Getter
    @Setter
    private String passwordConfirm;
}
