package gpse.team52.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security Configuration.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String H2_CONSOLE_URL_MATCHER = "h2-console/**";

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
    }
}
