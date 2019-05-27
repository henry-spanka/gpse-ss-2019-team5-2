package gpse.team52.web;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.NewPasswordForm;
import gpse.team52.form.PasswordResetMailForm;
import gpse.team52.form.UserRegistrationForm;
import gpse.team52.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Responsible for recovering a users password by mail.
 */
@SuppressWarnings("checkstyle:multiplestringliterals")
@Controller
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    /**
     * Show the forgot password email form to the user.
     *
     * @return Forgot password view.
     */
    @GetMapping("/recoverpw")
    public ModelAndView showRecoverPassword() {
        final PasswordResetMailForm passwordResetMailForm = new PasswordResetMailForm();

        return new ModelAndView("forgotpassword", "user", passwordResetMailForm);
    }

    /**
     * Try to register a user.
     *
     * @param form   The registration form received from the user.
     * @param result The result of the validation of the form.
     * @return A confirmation or an error.
     */
    @PostMapping("/recoverpw")
    public ModelAndView sendForgotPasswordMail(final @ModelAttribute("user") @Valid PasswordResetMailForm form,
                                 final BindingResult result) {

        String email = form.getEmail();
        System.out.println(email);

        userService.sendPasswordResetEmail(email);

        return new ModelAndView("forgotpasswordconfirm");
    }

    /**
     * Try to confirm a users account (their email).
     * @return Confirmation or error page.
     */
    @GetMapping("/forgotpasswordsetnew")
    public ModelAndView setNewPassword(@RequestParam("token") String token) {

        NewPasswordForm form = new NewPasswordForm();


        ModelAndView modelAndView = new ModelAndView("forgotpasswordsetnew", "user", form);
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    @PostMapping("/forgotpasswordsetnew")
    public ModelAndView setNewPasswordConfirm(final @RequestParam("token") String token,
                                              @ModelAttribute("user") @Valid NewPasswordForm form,
                                              final BindingResult result) {


        ModelAndView modelAndView = new ModelAndView("forgotpasswordsetnewconfirm");

        try {
            final User user = userService.findUserFromPasswordResetToken(UUID.fromString(token));
            modelAndView.addObject("user", user)
            .addObject("error", false);
            userService.setUserNewPassword(user, form.getPassword());
            System.out.println("Password changed for user: " + user.getFullName() + " changed to: " + user.getPassword());
        } catch (InvalidConfirmationTokenException | IllegalArgumentException e) {
            modelAndView.addObject("error", true);
        }

        return modelAndView;
    }
}
