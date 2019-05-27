package gpse.team52.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Equipment entity.
 */
@Entity
@NoArgsConstructor
public class Equipment {


    // TODO weitere Tabelle für Verbindung von Raum und Ausstattung machen
    //  (weil unterschiedliche Räume, unterschiedliche Ausstattung und Zustand und so)
    @Id
    @Getter
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

    public Equipment(final String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
