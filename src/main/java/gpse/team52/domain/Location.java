package gpse.team52.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Location Entity.
 */
@Entity
@NoArgsConstructor
public class Location {

    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID locationId;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @Getter
    @Column
    private String name;

    @Getter
    @Setter
    @Column
    private int timeoffset = 0;

    public Location(final String name) {
        this.name = name;
        this.timeoffset = 0;
    }
}
