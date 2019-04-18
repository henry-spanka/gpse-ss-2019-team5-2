package gpse.team52.validator.impl;

import gpse.team52.form.UserRegistrationForm;
import gpse.team52.validator.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates that the two fields 'password' and 'passwordConfirm' match.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserRegistrationForm userForm = (UserRegistrationForm) obj;

        if (userForm.getPassword() == null || userForm.getPasswordConfirm() == null) {
            return false;
        }

        if (userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
        .addPropertyNode("passwordConfirm").addConstraintViolation();

        return false;
    }
}
