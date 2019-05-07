package gpse.team52.web;

import java.util.UUID;

import javax.validation.Valid;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for registering a user.
 */
@SuppressWarnings("checkstyle:multiplestringliterals")
@Controller
public class RegisterController {
    @Autowired
    private UserService userService;

    /**
     * Show the registration form to the user.
     *
     * @return Register view.
     */
    @GetMapping("/register")
    public ModelAndView showRegister() {
        final UserRegistrationForm registrationForm = new UserRegistrationForm();

        return new ModelAndView("register", "user", registrationForm);
    }

    /**
     * Try to register a user.
     *
     * @param form   The registration form received from the user.
     * @param result The result of the validation of the form.
     * @return A confirmation or an error.
     */
    @PostMapping("/register")
    public ModelAndView register(final @ModelAttribute("user") @Valid UserRegistrationForm form,
                                 final BindingResult result) {

        if (!result.hasErrors()) {
            try {
                final User registeredUser = createUserAccount(form);

                return new ModelAndView("register-confirm", "registeredUser", registeredUser);
            } catch (UsernameExistsException e) {
                result.rejectValue("username", "register.username.existsError", e.getMessage());
            } catch (EmailExistsException e) {
                result.rejectValue("email", "register.email.existsError", e.getMessage());
            } catch (MailException e) {
                result.rejectValue("email", "register.email.mailSendError", e.getMessage());
            }
        }

        return new ModelAndView("register");
    }

    /**
     * Try to confirm a users account (their email).
     * @param token The token sent to their email address.
     * @return Confirmation or error page.
     */
    @GetMapping("/confirm-account")
    public ModelAndView confirmAccount(final @RequestParam("token") String token) {
        final ModelAndView modelAndView = new ModelAndView("register-confirmed");

        try {
            final User user = userService.validateUserFromToken(UUID.fromString(token));
            modelAndView.addObject("user", user)
            .addObject("error", false);
        } catch (InvalidConfirmationTokenException | IllegalArgumentException e) {
            modelAndView.addObject("error", true);
        }

        return modelAndView;
    }

    private User createUserAccount(final UserRegistrationForm form) throws UsernameExistsException,
    EmailExistsException, MailException {
        final User user = userService.createUser(form, "ROLE_USER");
        userService.sendVerificationEmail(user);

        return user;
    }
}
