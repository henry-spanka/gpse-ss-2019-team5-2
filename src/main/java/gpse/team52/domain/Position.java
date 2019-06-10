package gpse.team52.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Position entity.
 */
@Entity
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//NOPMD
    @Column(name = "positionId", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int positionID;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String positionName;

    @Getter
    @Setter
    @Column(nullable = false)
    private String rights;
}
