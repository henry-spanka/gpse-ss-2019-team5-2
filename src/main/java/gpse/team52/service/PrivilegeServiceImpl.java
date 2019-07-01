package gpse.team52.service;

import gpse.team52.contract.PrivilegeService;
import gpse.team52.domain.Privilege;
import gpse.team52.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeServiceImpl(final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public Iterable<Privilege> getAll() {
        return privilegeRepository.findAll();
    }

    @Override
    public Optional<Privilege> get(UUID privilegeId) {
        return privilegeRepository.findById(privilegeId);
    }

    @Override
    public Privilege create(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public Privilege update(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    @Override
    public void delete(Privilege privilege) {
        privilegeRepository.delete(privilege);
    }
}
