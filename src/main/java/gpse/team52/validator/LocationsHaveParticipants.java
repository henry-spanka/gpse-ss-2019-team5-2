package gpse.team52.validator;


import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import gpse.team52.validator.impl.LocationsHaveParticipantsValidator;


/**
 * Validates that for each location a number of participants exists.
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LocationsHaveParticipantsValidator.class)
@Documented
public @interface LocationsHaveParticipants {
    /**
     * Error message that indicates the reason why the check failed.
     *
     * @return The error message
     */
    String message() default "Number of participants for location invalid.";

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
