package gpse.team52.service;

import gpse.team52.contract.EquipmentService;
import gpse.team52.domain.Equipment;
import gpse.team52.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment createEquipment(String equipmentName) {
        Equipment equipment = new Equipment(equipmentName);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Iterable<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }
}
