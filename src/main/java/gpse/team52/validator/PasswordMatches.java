package gpse.team52.validator;


import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import gpse.team52.validator.impl.PasswordMatchesValidator;

/**
 * Validates that the two fields 'password' and 'passwordConfirm' match.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    /**
     * Error message that indicates the reason why the check failed.
     * @return The error message
     */
    String message() default "Passwords don't match";

    /**
     * Groups.
     * @return Nothing.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     * @return Nothing.
     */
    Class<? extends Payload>[] payload() default {};
}
