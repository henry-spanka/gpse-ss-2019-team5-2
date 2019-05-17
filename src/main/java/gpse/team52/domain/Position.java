package gpse.team52.domain;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID positionId;


    @Getter
    @Setter
    @Column(unique = true, nullable = false)
    private String positionName;

    @Getter
    @Setter
    @Column(nullable = false)
    private String rights;

    @Getter
    @ManyToMany(targetEntity = Right.class)
    @JoinColumn(nullable = false, name = "rightId")
    private List<Right> right = new ArrayList<>();

    public Position(String positionName, String rights){
        this.positionName = positionName;
        this.rights = rights;
    }


    public void addRight(Right right) {
        this.right.add(right);
    }

    public void addRights(List<Right> rights) {
        this.right.addAll(rights);
    }

    public void addRights(Right... rights) {
        addRights(Arrays.asList(rights));
    }
}
