package gpse.team52;

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
    private int getRoomID;

    @Column()
    private boolean defect;

    public Equipment() {
        this.equipmentName = equipmentName;
        this.consumable = consumable;
        this.getRoomID = getRoomID;
        this.defect = defect;
    }

    public Equipment(int equipmentID, String equipmentName, String consumable, int getRoomID, boolean defect) {
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public String getConsumable() {
        return consumable;
    }

    public int getGetRoomID() {
        return getRoomID;
    }

    public Boolean getDefect() {
        return defect;
    }
}
