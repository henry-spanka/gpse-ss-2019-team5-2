package gpse.team52.validator;


import gpse.team52.validator.impl.PasswordMatchesValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates that the two fields 'password' and 'passwordConfirm' match.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "Passwords don't match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
