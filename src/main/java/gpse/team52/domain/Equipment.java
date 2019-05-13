package gpse.team52.domain;

//import lombok.Getter;

import javax.persistence.*;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "equipmentId", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private int equipmentID;

    @Column(unique = true, nullable = false)
    private String equipmentName;

    @Column(nullable = false)
    private String consumable;

    @Column(nullable = false)
    private int roomID;

    @Column()
    private boolean defect;

    public Equipment(int equipmentID, String equipmentName, String consumable, int getRoomID, boolean defect) {
        this.equipmentName = equipmentName;
        this.consumable = consumable;
        this.roomID = getRoomID;
        this.defect = defect;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getConsumable() {
        return consumable;
    }

    public int getRoomID() {
        return roomID;
    }

    public Boolean getDefect() {
        return defect;
    }
}
