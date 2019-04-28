package gpse.team52.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Registers the AuthenticationService and PasswordEncoder to use.
 */
@Configuration
@ComponentScan
public class RoomBrokerConfiguration {
    @Autowired
    public void configureGlobal(final UserDetailsService userDetailsService,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManagerBuilder auth) throws Exception { //NOPMD
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
