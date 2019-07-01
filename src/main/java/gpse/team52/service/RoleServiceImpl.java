package gpse.team52.service;

import java.util.Optional;
import java.util.UUID;

import gpse.team52.contract.RoleService;
import gpse.team52.domain.Role;
import gpse.team52.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role service implementation.
 */
@Service
public class RoleServiceImpl implements RoleService {

    /**
     * Role repository.
     */
    private RoleRepository roleRepository;

    /**
     * Initialize role repository.
     *
     * @param roleRepository The role repository.
     */
    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Get all roles.
     *
     * @return all roles.
     */
    @Override
    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }

    /**
     * Get optional role from role Id.
     *
     * @param roleId The role Id.
     * @return optional role.
     */
    @Override
    public Optional<Role> get(UUID roleId) {
        return roleRepository.findById(roleId);
    }

    /**
     * Get optional role by name.
     *
     * @param name The role name.
     * @return optional role.
     */
    @Override
    public Optional<Role> getByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Create role.
     *
     * @param role The role.
     * @return the created role.
     */
    @Override
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Update role.
     *
     * @param role The role.
     * @return the updated role.
     */
    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    /**
     * Delete role.
     *
     * @param role The role.
     */
    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
