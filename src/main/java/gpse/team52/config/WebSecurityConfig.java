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

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
        .antMatchers("/", "/register", "/confirm-account", "/recoverpw", "/webjars/**").permitAll()
        .antMatchers("/h2-console/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().headers().frameOptions().sameOrigin()
        .and()
        .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
        .csrf()
        .ignoringAntMatchers("/h2-console/**")
        .and()
        .logout()
        .permitAll();
    }
}
