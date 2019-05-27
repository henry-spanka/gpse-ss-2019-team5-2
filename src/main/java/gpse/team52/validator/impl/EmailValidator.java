package gpse.team52.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gpse.team52.validator.ValidEmail;

/**
 * Email validator class which validates a field.
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@"
    + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private boolean nullable = false;
    private boolean empty = false;

    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
        empty = constraintAnnotation.empty();
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return validateEmail(email);
    }

    private boolean validateEmail(final String email) {
        if (email == null) {
            return nullable;
        }

        if (email.isEmpty()) {
            return empty;
        }

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
