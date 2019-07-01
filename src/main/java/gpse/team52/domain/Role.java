package gpse.team52.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Role Entity.
 */
@Entity
@NoArgsConstructor
public class Role {

    /**
     * Role primary key.
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Role name.
     */
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * List of privileges for a role.
     */
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Set<Privilege> privileges = new HashSet<>();

    /**
     * Role constructor.
     *
     * @param name The role name.
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Secondary role constructor.
     *
     * @param name       The role name.
     * @param privileges The role privileges.
     */
    public Role(String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }
}
