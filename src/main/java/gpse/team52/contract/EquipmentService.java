package gpse.team52.contract;

import gpse.team52.domain.Equipment;

/**
 * Equipment service public contact.
 */
public interface EquipmentService {
    Equipment createEquipment(String equipmentName);

    Iterable<Equipment> getAllEquipments();
}
