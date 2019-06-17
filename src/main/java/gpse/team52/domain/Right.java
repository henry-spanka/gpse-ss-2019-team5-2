package gpse.team52.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Right {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID rightId;

    @Getter
    @Column(unique = true, nullable = false)
    private String rightName;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "right")
    private List<Position> positions = new ArrayList<>();

    public Right(String rightName) {
        this.rightName = rightName;
    }
}
