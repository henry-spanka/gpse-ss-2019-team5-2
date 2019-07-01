package gpse.team52.seeder;

import gpse.team52.contract.PrivilegeService;
import gpse.team52.domain.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Initialize default rights in the database.
 */

@Service
public class InitializeDefaultPrivilege {
    private final PrivilegeService privilegeService;

    /**
     * Constructor for the used services.
     * @param privilegeService Service for rights
     */
    @Autowired
    public InitializeDefaultPrivilege(final PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    /**
     * Initializes rights to test.
     */
    @PostConstruct
    public void init() {
        privilegeService.create(new Privilege("create_user"));
        privilegeService.create(new Privilege("delete_user"));
        privilegeService.create(new Privilege("create_meeting"));
        privilegeService.create(new Privilege("delete_meeting"));
        privilegeService.create(new Privilege("update_meeting"));
        privilegeService.create(new Privilege("disable_rebook_meeting"));
    }
}
