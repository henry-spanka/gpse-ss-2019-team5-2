package gpse.team52.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
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
    @ManyToMany(mappedBy = "equipment")
    private List<Room> rooms = new ArrayList<>();

    public Equipment(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
