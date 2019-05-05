package gpse.team52.validator;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import gpse.team52.validator.impl.PasswordComplexityValidator;

/**
 * Annotation that validates a password complexity.
 */
@Documented
@Constraint(validatedBy = PasswordComplexityValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    /**
     * Message that indicates why the validation failed.
     * @return Validation error message.
     */
    String message() default "Uppercase characters and digits required, min length 8.";

    /**
     * Groups.
     * @return The groups.
     */
    Class<?>[] groups() default {};

    /**
     * Payload.
     * @return The Payload.
     */
    Class<? extends Payload>[] payload() default {};

}
