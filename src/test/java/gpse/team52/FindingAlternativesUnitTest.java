package gpse.team52;


import gpse.team52.contract.RoomFinderService;
import gpse.team52.contract.RoomService;
import gpse.team52.domain.Equipment;
import gpse.team52.domain.Location;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;
import gpse.team52.repository.EquipmentRepository;
import gpse.team52.repository.LocationRepository;
import gpse.team52.repository.MeetingRepository;
import gpse.team52.repository.RoomRepository;
import gpse.team52.service.RoomFinderServiceImpl;
import gpse.team52.service.RoomServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FindingAlternativesUnitTest {

    private RoomFinderService roomFinderService;
    private RoomService roomService;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MeetingRepository meetingRepository;

    @Mock
    private EquipmentRepository equipmentRepository;

    @Mock
    private LocationRepository locationRepository;

    private Room bielefeld1;
    private Room bielefeld2;
    private Room bielefeld3;

    private Room helpup1;
    private Room helpup2;
    private Room helpup3;

    private Room lage1;
    private Room lage2;
    private Room lage3;

    private Meeting meetingBielefeld1;
    private Meeting meetingHelpup1;
    private Meeting meetingLage1;

    private Location bielefeld;
    private Location helpup;
    private Location lage;

    @BeforeEach
    void setUp() {

        roomFinderService = new RoomFinderServiceImpl(roomRepository, meetingRepository);
        roomService = new RoomServiceImpl(roomRepository, locationRepository, equipmentRepository);

        bielefeld = new Location("Bielefeld");
        helpup = new Location("Helpup");
        lage = new Location("Lage");

        // seats, extraSeats, mail, location, name, layout
        bielefeld1 = new Room(10,10,"bf1@example.de", bielefeld, "Bf1", "layoutRed");

        bielefeld2 = new Room(20, 5, "bf2@example.de", bielefeld, "Bf2", "layoutRed");
        roomService.createRoom(20, 5, "bf2@example.de", bielefeld, "Bf2", "layoutRed");
        roomService.update(bielefeld2);

/*
        userRegistrationForm = new UserRegistrationForm();
        userRegistrationForm.setFirstName("Test");
        userRegistrationForm.setLastName("User");
        userRegistrationForm.setEmail(EMAIL);
        userRegistrationForm.setUsername(USERNAME);
        userRegistrationForm.setPassword("mysecretpassword");
        userRegistrationForm.setPasswordConfirm("mysecretpassword");

        testUser = new User(userRegistrationForm, "encoded_pw");

 */

    }

    @Test
    void findAlternativesForOne() {
        final String[] result = {"rnbqk1nr/1ppp1p1p/p6b/3KpPp1/3P4/8/PPP1P1PP/RNBQ1BNR", "b"};
        Assertions.assertThat(result).containsExactly("rnbqk1nr/1ppp1p1p/p6b/3KpPp1/3P4/8/PPP1P1PP/RNBQ1BNR", "b");
        // just to test whether test is running right now

        //Iterable<Room> allRooms = roomRepository.findAll();
        //Assertions.assertThat(allRooms).containsExactlyInAnyOrder(bielefeld1, bielefeld2);
    }

/*
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

 */

}
