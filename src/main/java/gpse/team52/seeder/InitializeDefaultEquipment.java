package gpse.team52.seeder;

import gpse.team52.contract.EquipmentService;
import gpse.team52.domain.Equipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Initializes the Default Equipment in the database.
 */
@Service
public class InitializeDefaultEquipment {

    private EquipmentService equipmentService;

    @Autowired
    public InitializeDefaultEquipment() {
        this.equipmentService = equipmentService;
    }
//TODO: rausfinden wie man die passende roomID hinterlegt
    final Equipment equipment1 = new Equipment("table_1", "chalk", false);
    final Equipment equipment2 = new Equipment( "table_2", "chalk", false);
    final Equipment equipment3 = new Equipment( "whiteboard_1", "pen", true);
    final Equipment equipment4 = new Equipment("whiteboard_2", "pen", false);
    final Equipment equipment5 = new Equipment("beamer_1", "", true);
}
