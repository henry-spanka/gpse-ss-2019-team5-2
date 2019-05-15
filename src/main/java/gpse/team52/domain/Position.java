package gpse.team52.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    public Position() {
    }
}
