package gpse.team52.validator.impl;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gpse.team52.validator.ValidPassword;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

/**
 * Validates the Complexity of passwords.
 */
public class PasswordComplexityValidator implements ConstraintValidator<ValidPassword, String> {
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 30;

    @Override
    public void initialize(final ValidPassword arg0) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
        new LengthRule(MIN_LENGTH, MAX_LENGTH),
        new UppercaseCharacterRule(1),
        new DigitCharacterRule(1),
        new WhitespaceRule()));

        final RuleResult result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
        .addConstraintViolation();

        return false;
    }
}
