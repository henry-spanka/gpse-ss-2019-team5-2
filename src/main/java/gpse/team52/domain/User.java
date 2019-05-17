package gpse.team52.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import gpse.team52.form.UserRegistrationForm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User entity.
 */
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    private static final long serialVersionUID = 7179581269044235932L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String username;

    @Getter
    @Setter
    @Column(nullable = false)
    private String firstname;

    @Getter
    @Setter
    @Column(nullable = false)
    private String lastname;

    @Column(nullable = true)
    private String location;

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String email;

    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean isEnabled = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    /**
     * Create a user from a registration form.
     *
     * @param form     The form from which the user details should be retrieved.
     * @param password The encoded password for the user.
     */
    public User(final UserRegistrationForm form, final String password) {
        username = form.getUsername();
        firstname = form.getFirstName();
        lastname = form.getLastName();
        email = form.getEmail();
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


    public String getLocation() {return location;}


    public void setLocation(String location) {this.location = location;}

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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullName() {
        return firstname + ' ' + lastname;
    }

    /**
     * Assign a role to a user.
     *
     * @param role The rule to be added.
     */
    public void addRole(final String role) {
        if (roles == null) {
            this.roles = new ArrayList<>();
        }

        this.roles.add(role);
    }


}
