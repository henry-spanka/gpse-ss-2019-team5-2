package gpse.team52.service;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.EmailNotFoundException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import gpse.team52.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Service implementation.
 */
@Service
@Primary
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    /**
     * User Service implementation.
     * @param userRepository The user data repository.
     */
    @Autowired
    public UserServiceImpl(final UserRepository userRepository, @Lazy final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found."));
    }

    @Override
    public UserDetails loadUserByEmail(final String email) throws EmailNotFoundException {
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new EmailNotFoundException("Email " + email + " not found."));
    }

    @Override
    public User createUser(UserRegistrationForm form, final String... roles) throws UsernameExistsException, EmailExistsException {
        if (emailExists(form.getEmail())) {
            throw new EmailExistsException("Email " + form.getEmail() + " already exists.");
        }

        if (usernameExists(form.getUsername())) {
            throw new UsernameExistsException("Username " + form.getUsername() + " already exists.");
        }

        String encodedPassword = passwordEncoder.encode(form.getPassword());
        final User user = new User(form, encodedPassword);

        for (final String role : roles) {
            user.addRole(role);
        }

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        try {
            loadUserByEmail(email);
        } catch (EmailNotFoundException e) {
            return false;
        }

        return true;
    }

    private boolean usernameExists(String username) {
        try {
            loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return false;
        }

        return true;
    }
}
