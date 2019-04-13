package gpse.team52.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User entity.
 */
@Entity
public class User implements UserDetails {

    @Id
    @Column
    private String username;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    /**
     * Default Constructor required by Spring.
     */
    protected User() {

    }

    /**
     * Create a new User.
     * @param username The username.
     * @param firstname The first name.
     * @param lastname The last name.
     * @param password The password.
     */
    public User(final String username, final String firstname, final String lastname, final String password) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    /**
     * Assign a role to a user.
     * @param role The rule to be added.
     */
    public void addRole(final String role) {
        if (roles == null) {
            this.roles = new ArrayList<>();
        }

        this.roles.add(role);
    }


}
