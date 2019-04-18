package gpse.team52.validator;

import gpse.team52.validator.impl.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
