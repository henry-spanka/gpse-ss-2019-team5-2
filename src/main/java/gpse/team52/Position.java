package gpse.team52;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
