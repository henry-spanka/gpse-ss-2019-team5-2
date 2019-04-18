package gpse.team52.validator.impl;

import gpse.team52.validator.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email validator class which validates a field.
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" +
    "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    @Override
    public void initialize(final ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        return validateEmail(email);
    }

    private boolean validateEmail(final String email) {
        if (email == null) {
            return false;
        }

        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
