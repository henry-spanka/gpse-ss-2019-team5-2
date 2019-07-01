package gpse.team52.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    @Getter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilege_id", referencedColumnName = "id")
    private Set<Privilege> privileges = new HashSet<>();

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, Set<Privilege> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public void addPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
    }
}
