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
    String message() default "Invalid email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
