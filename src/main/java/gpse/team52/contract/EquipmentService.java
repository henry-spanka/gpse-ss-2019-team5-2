package gpse.team52.contract;

import gpse.team52.domain.Equipment;

public interface EquipmentService {
    Equipment createEquipment(String equipmentName);

    Iterable<Equipment> getAllEquipments();
}
