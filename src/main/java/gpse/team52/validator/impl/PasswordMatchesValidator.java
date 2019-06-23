package gpse.team52.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import gpse.team52.contract.HasPassword;
import gpse.team52.validator.PasswordMatches;

/**
 * Validates that the two fields 'password' and 'passwordConfirm' match.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final HasPassword userForm = (HasPassword) obj;

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
