package gpse.team52.service;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.contract.PrivilegeService;
import gpse.team52.domain.Privilege;
import gpse.team52.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Privilege service implementation.
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    /**
     * Privilege repository.
     */
    private final PrivilegeRepository privilegeRepository;

    /**
     * Initialize privilege repository.
     *
     * @param privilegeRepository The privilege repository.
     */
    @Autowired
    public PrivilegeServiceImpl(final PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }

    /**
     * Get all privileges.
     *
     * @return all privileges.
     */
    @Override
    public Iterable<Privilege> getAll() {
        return privilegeRepository.findAll();
    }

    /**
     * Get optional privilege from privilege Id.
     *
     * @param privilegeId The privilege Id.
     * @return optional privilege.
     */
    @Override
    public Optional<Privilege> get(UUID privilegeId) {
        return privilegeRepository.findById(privilegeId);
    }

    /**
     * Get optional privilige by name.
     *
     * @param name The privilege name.
     * @return optional privilege.
     */
    @Override
    public Optional<Privilege> getByName(String name) {
        return privilegeRepository.findByName(name);
    }

    /**
     * Create privilege.
     *
     * @param privilege The privilege.
     * @return the created privilege.
     */
    @Override
    public Privilege create(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    /**
     * Update privilege.
     *
     * @param privilege The privilege.
     * @return the updated privilege.
     */
    @Override
    public Privilege update(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }

    /**
     * Delete privilege.
     *
     * @param privilege The privilege.
     */
    @Override
    public void delete(Privilege privilege) {
        privilegeRepository.delete(privilege);
    }
}
