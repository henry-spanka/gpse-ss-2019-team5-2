package gpse.team52.domain;

import gpse.team52.form.UserRegistrationForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User entity.
 */
@Entity

public class User implements UserDetails { //NOPMD

    private static final long serialVersionUID = 7179581269044235932L;

    @Id
    @Getter
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

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Getter
    @Setter
    @Column
    private String picture;

    @Column(unique = true, nullable = false)
    @Getter
    @Setter
    private String email;

    @Setter
    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false)
    private boolean isEnabled = false;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Set<Role> roles = new HashSet<>();

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Set<Privilege> privileges = new HashSet<>();

    @Getter
    @Column(nullable = true, unique = true)
    private UUID iCalToken;

    /**
     * Create a user from a registration form.
     *
     * @param form     The form from which the user details should be retrieved.
     * @param password The encoded password for the user.
     */
    public User(final UserRegistrationForm form, final String password) {
        this();

        username = form.getUsername();
        firstname = form.getFirstName();
        lastname = form.getLastName();
        email = form.getEmail();
        this.password = password;

        if (form.getLocation() != null) {
            location = form.getLocation();
        }
    }

    /**
     * Create a new user entity with a random iCalToken.
     */
    public User() {
        this.iCalToken = UUID.randomUUID();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.stream().map(Role::getName).collect(Collectors.joining()));
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

    public String getFullName() {
        return firstname + ' ' + lastname;
    }

    /**
     * Assign a role to a user.
     *
     * @param role The rule to be added.
     */
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }

    public String userToString() {
        StringBuilder rolesString = new StringBuilder();
        if (roles.size() != 0) {
            rolesString = new StringBuilder(roles.toArray(Role[]::new)[0].getName());
            for (int i = 1; i < roles.size(); i++) {
                rolesString.append(",").append(roles.toArray(Role[]::new)[i].getName());
            }

        }

        return username + ";" + firstname + ";" + lastname + ";" + email + ";" + rolesString;
    }
}
