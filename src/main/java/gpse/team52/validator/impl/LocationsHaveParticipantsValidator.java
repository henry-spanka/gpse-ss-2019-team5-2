package gpse.team52.validator.impl;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gpse.team52.form.MeetingCreationForm;
import gpse.team52.validator.LocationsHaveParticipants;

/**
 * Validates that for each location a number of participants exists.
 */
public class LocationsHaveParticipantsValidator implements ConstraintValidator<LocationsHaveParticipants, Object> {
    @Override
    public void initialize(final LocationsHaveParticipants constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final MeetingCreationForm creationForm = (MeetingCreationForm) obj;

        final Map<String, Integer> participants = creationForm.getParticipants();

        boolean hasErrors = false;

        if (creationForm.getLocations() != null) {
            for (final String locationId : creationForm.getLocations()) {
                if (participants == null || !participants.containsKey(locationId)) {
                    addConstraintViolation(context, "participants[" + locationId + "]");
                    hasErrors = true;
                } else {
                    final Integer value = participants.get(locationId);

                    if (value == null || value <= 0) {
                        addConstraintViolation(context, "participants[" + locationId + "]");
                        hasErrors = true;
                    }
                }
            }
        }

        return !hasErrors;
    }

    private void addConstraintViolation(final ConstraintValidatorContext context, final String node) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
        .addPropertyNode(node).addConstraintViolation();
    }
}
