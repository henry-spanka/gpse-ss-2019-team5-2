package gpse.team52.service;

import java.util.Optional;

import gpse.team52.contract.EquipmentService;
import gpse.team52.domain.Equipment;
import gpse.team52.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Equipment service implementation.
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public Equipment createEquipment(final String equipmentName) {
        final Equipment equipment = new Equipment(equipmentName);
        return equipmentRepository.save(equipment);
    }

    @Override
    public Iterable<Equipment> getAllEquipments() {
        return null;
    }

    @Override
    public Optional<Equipment> getEquipment(final String name) {
            return equipmentRepository.findByEquipmentName(name);
    }
}
