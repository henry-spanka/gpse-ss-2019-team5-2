package gpse.team52.contract;

import gpse.team52.domain.Equipment;


public interface EquipmentService {
    Equipment createEquipment(String equipmentName, String consumable, int getRoomID, boolean defect);

    Iterable<Equipment> getAllEquipments();
}
