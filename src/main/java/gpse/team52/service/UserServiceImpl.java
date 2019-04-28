package gpse.team52.service;

import java.util.UUID;

import gpse.team52.contract.UserService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.ConfirmationToken;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.EmailNotFoundException;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import gpse.team52.repository.ConfirmationTokenRepository;
import gpse.team52.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * User Service implementation.
 */
@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    /**
     * User Service implementation.
     *
     * @param userRepository              The user data repository.
     * @param confirmationTokenRepository The confirmation token repository.
     * @param passwordEncoder             The password encoder to use.
     * @param mailService                 The mail service to use.
     */
    @Autowired
    public UserServiceImpl(
    final UserRepository userRepository,
    final ConfirmationTokenRepository confirmationTokenRepository,
    final PasswordEncoder passwordEncoder,
    final MailService mailService) {
        this.userRepository = userRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public User loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found."));
    }

    @Override
    public User loadUserByEmail(final String email) throws EmailNotFoundException {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new EmailNotFoundException("Email " + email + " not found."));
    }

    @Override
    public User createUser(final UserRegistrationForm form, final String... roles) throws UsernameExistsException,
    EmailExistsException {
        return createUser(form, false, roles);
    }

    @Override
    public User createUser(final UserRegistrationForm form, final boolean enabled, final String... roles)//NOPMD
    throws UsernameExistsException,
    EmailExistsException {
        if (emailExists(form.getEmail())) {
            throw new EmailExistsException("Email " + form.getEmail() + " already exists.");
        }

        if (usernameExists(form.getUsername())) {
            throw new UsernameExistsException("Username " + form.getUsername() + " already exists.");
        }

        final String encodedPassword = passwordEncoder.encode(form.getPassword());
        final User user = new User(form, encodedPassword);
        user.setEnabled(enabled);

        for (final String role : roles) {
            user.addRole(role);
        }

        return userRepository.save(user);
    }

    /**
     * Send a verification email to the user's email address.
     *
     * @param user The User to verify.
     */
    @Override
    public void sendVerificationEmail(final User user) {
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        final ModelAndView modelAndView = new ModelAndView("email/register-verification.html", "user", user);
        modelAndView.addObject("token", confirmationToken);

        mailService.sendEmailTemplateToUser(user, "Email Verification", modelAndView);
    }

    @Override
    public User validateUserFromToken(final UUID token) throws InvalidConfirmationTokenException {
        final ConfirmationToken confirmationToken = confirmationTokenRepository.findById(token)
        .orElseThrow(() -> new InvalidConfirmationTokenException("The token " + token + " is invalid."));

        final User user = confirmationToken.getUser();
        user.setEnabled(true);

        userRepository.save(user);
        confirmationTokenRepository.delete(confirmationToken);

        return user;
    }

    private boolean emailExists(final String email) {
        try {
            loadUserByEmail(email);
        } catch (EmailNotFoundException e) {
            return false;
        }

        return true;
    }

    private boolean usernameExists(final String username) {
        try {
            loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return false;
        }

        return true;
    }
}
