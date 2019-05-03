package gpse.team52.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import gpse.team52.validator.impl.EmailValidator;

/**
 * Email validator which validates a field.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    /**
     * Error message that indicates the reason why the check failed.
     *
     * @return The error message.
     */
    String message() default "Invalid email";

    /**
     * Groups.
     *
     * @return Nothing.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     *
     * @return Nothing.
     */
    Class<? extends Payload>[] payload() default {};
}
