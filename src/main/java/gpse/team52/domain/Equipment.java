package gpse.team52.domain;

//import lombok.Getter;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Equipment {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, updatable = false, columnDefinition = "BINARY(16)")
    private UUID equipmentID;

    @Column(unique = true, nullable = false)
    private String equipmentName;

    @Column(nullable = false)
    private String consumable;

    public void setEquipmentID(UUID equipmentID) {
        this.equipmentID = equipmentID;
    }

    @Column(nullable = false)
    private int roomID;

    @Column()
    private boolean defect;

    public Equipment( String equipmentName, String consumable, int getRoomID, boolean defect) {
        this.equipmentName = equipmentName;
        this.consumable = consumable;
        this.roomID = getRoomID;
        this.defect = defect;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getConsumable() {
        return consumable;
    }

    public void setConsumable(String consumable) {
        this.consumable = consumable;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public Boolean getDefect() {
        return defect;
    }

    public void setDefect(boolean defect) {
        this.defect = defect;
    }
}
