package gpse.team52.seeder;

import gpse.team52.contract.*;
import gpse.team52.domain.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Initialize default rights in the database.
 */

@Service
public class InitializeDefaultRights {
    private final String rightName = "rightName";
    private final RightService rightService;

    /**
     * Constructor for the used services.
     *
     * @param rightService Service for rights
     */
    @Autowired
    public InitializeDefaultRights(final RightService rightService) {
        this.rightService = rightService;
    }

    /**
     * Initializes rights to test.
     */
    @PostConstruct
    public void init() {
        final Right right1 = new Right("create_user");
        final Right right2 = new Right("delete_user");
        final Right right3 = new Right("create_meeting");
        final Right right4 = new Right("delete_meeting");
        final Right right5 = new Right("delete_meeting");
        final Right right6 = new Right("disable_rebook_meeting");

        rightService.update(right1);
        rightService.update(right2);
        rightService.update(right3);
        rightService.update(right4);
        rightService.update(right5);
        rightService.update(right6);
    }
}
