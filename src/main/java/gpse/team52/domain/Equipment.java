package gpse.team52.domain;

//import lombok.Getter;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Equipment {


    // TODO weitere Tabelle für Verbundung von Ruam und Ausstattung machen (weil unterschiedliche Räume, unterschieliche Ausstattung und ZUstand und so)
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID equipmentID;

    @Getter
    @Column(unique = true, nullable = false)
    private String equipmentName;

    @Setter
    @Getter
    @Column(nullable = false)
    private String consumable;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "equipment")
    private List<Room> rooms;

    @Getter
    @Setter
    @Column()
    private boolean defect;

    public Equipment(String equipmentName, String consumable, boolean defect) {
        this.equipmentName = equipmentName;
        this.consumable = consumable;
        this.defect = defect;
    }
}
