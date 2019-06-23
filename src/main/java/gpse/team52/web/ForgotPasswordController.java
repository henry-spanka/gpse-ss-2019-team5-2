package gpse.team52.web;

import java.util.UUID;

import javax.validation.Valid;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.form.NewPasswordForm;
import gpse.team52.form.PasswordResetMailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Responsible for recovering a users password by mail.
 */
@SuppressWarnings("checkstyle:multiplestringliterals")
@Controller
public class ForgotPasswordController {
    /**
     * The userService required.
     */
    private final UserService userService; //NOPMD

    /**
     * Constructor for the userService.
     *
     * @param userService The corresponding userService used
     */
    @Autowired
    public ForgotPasswordController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Show the forgot password email form to the user.
     *
     * @return Forgot password view.
     */
    @GetMapping("/recoverpw")
    public ModelAndView showRecoverPassword() {
        final PasswordResetMailForm passwordResetMailForm = new PasswordResetMailForm(); //NOPMD

        return new ModelAndView("forgotpassword", "user", passwordResetMailForm); //NOPMD
    }

    /**
     * Try to register a user.
     *
     * @param form The registration form received from the user.
     * @return A confirmation or an error.
     */
    @PostMapping("/recoverpw")
    public ModelAndView sendForgotPasswordMail(final @ModelAttribute("user") @Valid PasswordResetMailForm form) {

        final String email = form.getEmail();
        //System.out.println(email);

        userService.sendPasswordResetEmail(email);

        return new ModelAndView("forgotpasswordconfirm");
    }

    /**
     * Try to confirm a users account (their email).
     *
     * @return Confirmation or error page.
     */
    @GetMapping("/forgotpasswordsetnew")
    public ModelAndView setNewPassword(@RequestParam("token") final String token) { //NOPMD
        final NewPasswordForm form = new NewPasswordForm();
        final ModelAndView modelAndView = new ModelAndView("forgotpasswordsetnew", "user", form);
        modelAndView.addObject("token", token);
        return modelAndView;
    }

    /**
     * Validating the users successful attempt at changing their password.
     *
     * @param token The token to verify the user.
     * @param form  The new password form to set the users new password.
     * @return Forgot password view.
     */
    @PostMapping("/forgotpasswordsetnew")
    public ModelAndView setNewPasswordConfirm(final @RequestParam("token") String token, //NOPMD
                                              @ModelAttribute("user") @Valid final NewPasswordForm form) {

        final ModelAndView modelAndView = new ModelAndView("forgotpasswordsetnewconfirm");
        try {
            final User user = userService.findUserFromPasswordResetToken(UUID.fromString(token));
            modelAndView.addObject("user", user)//NOPMD
            .addObject("error", false);
            userService.setUserNewPassword(user, form.getPassword());
        } catch (InvalidConfirmationTokenException | IllegalArgumentException e) {
            modelAndView.addObject("error", true);
        }
        return modelAndView;
    }
}
