package gpse.team52.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Privilege Entity.
 */
@Entity
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Privilege {
    /**
     * Primary key for every privilege.
     */
    @Id
    @Getter
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    /**
     * Privilege name.
     */
    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Privilege constructor
     *
     * @param name The privilege name.
     */
    public Privilege(String name) {
        this.name = name;
    }
}
