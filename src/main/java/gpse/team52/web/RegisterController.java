package gpse.team52.web;

import gpse.team52.form.UserRegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Responsible for registering a user.
 */
@Controller
public class RegisterController {
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
    public ModelAndView register(final @ModelAttribute("user") @Valid UserRegistrationForm form, final BindingResult result) {

        if (result.hasErrors()) {
            return new ModelAndView("register", "user", form);
        }

        return new ModelAndView("register-confirm");
    }
}
