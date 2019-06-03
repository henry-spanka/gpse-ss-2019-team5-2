package gpse.team52;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
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
import gpse.team52.repository.ForgotPasswordTokenRepository;
import gpse.team52.repository.UserRepository;
import gpse.team52.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceUnitTest {
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Mock
    private ForgotPasswordTokenRepository forgotPasswordTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    private User testUser;
    private UserRegistrationForm userRegistrationForm;

    private static final String USERNAME = "testuser";
    private static final String EMAIL = "test.user@example.org";

    @BeforeEach
    public void setUp() {

        userService = new UserServiceImpl(userRepository, confirmationTokenRepository, forgotPasswordTokenRepository, passwordEncoder, mailService);

        userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.setFirstName("Test");
        userRegistrationForm.setLastName("User");
        userRegistrationForm.setEmail(EMAIL);
        userRegistrationForm.setUsername(USERNAME);
        userRegistrationForm.setPassword("mysecretpassword");
        userRegistrationForm.setPasswordConfirm("mysecretpassword");

        testUser = new User(userRegistrationForm, "encoded_pw");
    }

    @Test
    public void validUserCanBeFound() throws EmailNotFoundException {
        when(userRepository.findByEmail(userRegistrationForm.getEmail())).thenReturn(Optional.of(testUser));
        when(userRepository.findByUsername(userRegistrationForm.getUsername())).thenReturn(Optional.of(testUser));

        User user = userService.loadUserByUsername(USERNAME);

        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getEmail()).isEqualTo(EMAIL);

        user = userService.loadUserByEmail(EMAIL);

        assertThat(user.getUsername()).isEqualTo(USERNAME);
    }

    @Test
    public void invalidUserCannotBeFound() {
        when(userRepository.findByEmail(userRegistrationForm.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userRegistrationForm.getUsername())).thenReturn(Optional.empty());

        assertThatExceptionOfType(EmailNotFoundException.class).isThrownBy(() -> userService.loadUserByEmail(userRegistrationForm.getEmail()));

        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> userService.loadUserByUsername(userRegistrationForm.getUsername()));
    }


    @Test
    public void userIsEnabledAfterTokenValidation() throws InvalidConfirmationTokenException {
        final UUID uuid = UUID.randomUUID();

        final ConfirmationToken confirmationToken = new ConfirmationToken(testUser, uuid);
        when(confirmationTokenRepository.findById(uuid)).thenReturn(Optional.of(confirmationToken));

        final User user = userService.validateUserFromToken(uuid);

        assertThat(user.isEnabled()).isTrue();
    }

    @Test
    public void invalidValidationTokenThrowsException() {
        final UUID uuid = UUID.randomUUID();

        when(confirmationTokenRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatExceptionOfType(InvalidConfirmationTokenException.class).isThrownBy(() -> userService.validateUserFromToken(uuid));
    }

    @Test
    public void userCanBeRegistered() throws UsernameExistsException, EmailExistsException {
        when(userRepository.save(any())).then(returnsFirstArg());
        when(userRepository.findByEmail(userRegistrationForm.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userRegistrationForm.getUsername())).thenReturn(Optional.empty());

        final User user = userService.createUser(userRegistrationForm, "ROLE_USER");

        assertThat(user.getUsername()).isEqualTo(userRegistrationForm.getUsername());
        assertThat(user.getEmail()).isEqualTo(userRegistrationForm.getEmail());
        assertThat(user.getFirstname()).isEqualTo(userRegistrationForm.getFirstName());
        assertThat(user.getLastname()).isEqualTo(userRegistrationForm.getLastName());
        assertThat(user.getAuthorities().toString()).isEqualTo("[ROLE_USER]");
        assertThat(user.isEnabled()).isFalse();
    }

    @Test
    public void userCannotBeRegisteredIfEmailExists() {
        when(userRepository.findByEmail(userRegistrationForm.getEmail())).thenReturn(Optional.of(testUser));

        assertThatExceptionOfType(EmailExistsException.class).isThrownBy(() -> userService.createUser(userRegistrationForm, "ROLE_USER"));
    }

    @Test
    public void userCannotBeRegisteredIfUsernameExists() {
        when(userRepository.findByEmail(userRegistrationForm.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(userRegistrationForm.getUsername())).thenReturn(Optional.of(testUser));

        assertThatExceptionOfType(UsernameExistsException.class).isThrownBy(() -> userService.createUser(userRegistrationForm, "ROLE_USER"));
    }
}
