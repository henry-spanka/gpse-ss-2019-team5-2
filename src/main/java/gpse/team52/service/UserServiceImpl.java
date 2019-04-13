package gpse.team52.service;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User Service implementation.
 */
@Service
@Primary
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * User Service implementation
     * @param userRepository The user data repository.
     */
    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
        .orElseThrow(() -> new UsernameNotFoundException("User name " + username + " not found."));
    }

    @Override
    public User createUser(
    final String username,
    final String password,
    final String firstname,
    final String lastname,
    final String... roles) {
        final User user = new User(username, firstname, lastname, password);
        for (final String role : roles) {
            user.addRole(role);
        }

        return userRepository.save(user);
    }
}
