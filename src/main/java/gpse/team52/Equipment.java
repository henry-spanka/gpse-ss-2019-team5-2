package gpse.team52;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipmentId", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int equipmentID;

    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String equipmentName;

    @Getter
    @Setter
    @Column(nullable = false)
    private String consumable;

    @Getter
    @Setter
    @Column(nullable = false)
    private int getRoomID;

    @Getter
    @Setter
    @Column
    private boolean defect;

    public Equipment() {
            }


}
