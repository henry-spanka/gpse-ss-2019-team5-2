package gpse.team52.domain;

import gpse.team52.contract.StartMeetingService;
import gpse.team52.contract.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InitializeDatabase implements InitializingBean {

    private final UserService userService;

    private final StartMeetingService startMeetingService;

    @Autowired
    public InitializeDatabase(final UserService userService, final StartMeetingService startMeetingService) {
        this.userService = userService;
        this.startMeetingService = startMeetingService;
    }


    @Override
    public void afterPropertiesSet() {
        try {
            userService.loadUserByUsername("Uncle_Bob");
        } catch (UsernameNotFoundException ex) {
            final User user = userService.createUser("Uncle_Bob",
            "{bcrypt}$2a$10$WoG5Z4YN9Z37EWyNCkltyeFr6PtrSXSLMeFWOeDUwcanht5CIJgPa",
            "Bob", "Martin", "ROLE_USER");

            blogService.addArticle(user, "mein erster eintrag", "lorem ipsum dolor sit amet...");
            blogService.addArticle(user, "mein zweiter eintrag",
            "Wants pawn term, dare worsted ladle gull hoe lift wetter murder inner ladle cordage, "
            + "honor itch offer lodge dock florist. ");
        }
    }
}
