package gpse.team52.seeder;

import gpse.team52.contract.PositionService;
import gpse.team52.contract.RightService;
import gpse.team52.domain.Position;
import gpse.team52.domain.Right;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

/**
 * Initialize default positions in the database.
 */

@Service
public class InitializeDefaultPositions {
    private final String positionName = "positionName";
    private final List<Right> rights = new ArrayList<>();
    private final PositionService positionService;
    private final RightService rightService;

    /**
     * Constructor for the used services.
     *
     * @param positionService Service for positions
     * @param rightService    Service for positios
     */
    @Autowired
    public InitializeDefaultPositions(final PositionService positionService, final RightService rightService) {
        this.positionService = positionService;
        this.rightService = rightService;
    }

    /**
     * Initializes rights to test.
     */
    @PostConstruct
    public void init() {
        Iterable<Right> rights = this.rightService.getAllRights();
        Hashtable<String, Right> hashtable = new Hashtable<>();
        for (Right right : rights) {
            hashtable.put(right.getRightName(), right);
        }
        ArrayList<Right> allRights = new ArrayList<>(hashtable.values());


        final Position position1 = new Position("admin", allRights);
        final Position position2 = new Position("student", Arrays.asList(hashtable.get("create_meeting")));
        final Position position3 = new Position("secretary", Arrays.asList(hashtable.get("create_meeting"),
        hashtable.get("delete_meeting"),hashtable.get("disable_rebook_meeting")));


        positionService.update(position1);
        positionService.update(position2);
        positionService.update(position3);
    }

}
