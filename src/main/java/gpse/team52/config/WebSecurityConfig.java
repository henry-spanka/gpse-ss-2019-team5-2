package gpse.team52.config;

import gpse.team52.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security Configuration.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String H2_CONSOLE_URL_MATCHER = "/h2-console/**";

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/", "/register", "/confirm-account", "/recoverpw", "/webjars/**").permitAll()
        .antMatchers(H2_CONSOLE_URL_MATCHER).hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().headers().frameOptions().sameOrigin()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .csrf()
        .ignoringAntMatchers(H2_CONSOLE_URL_MATCHER)
        .and()
        .logout()
        .permitAll();

        //allow file upload
        http.csrf().disable().authorizeRequests().anyRequest().authenticated();

    }

    @Autowired
    public void configureGlobal(final UserService userDetailsService,
                                final PasswordEncoder passwordEncoder,
                                final AuthenticationManagerBuilder auth) throws Exception { //NOPMD
        auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
    }
}
