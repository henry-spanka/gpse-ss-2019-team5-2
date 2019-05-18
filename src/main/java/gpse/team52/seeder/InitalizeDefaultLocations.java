package gpse.team52.seeder;

import gpse.team52.domain.Location;
import gpse.team52.contract.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Initializes Default Locations in the database.
 */
@Service
public class InitalizeDefaultLocations {

    private final LocationService locationService;

    @Autowired
    public InitalizeDefaultLocations(final LocationService locationService) {
        this.locationService = locationService;
    }

    @PostConstruct
    public void init() {
        Location loc1 = new Location("Düsseldorf");
        Location loc2 = new Location("Bonn");
        Location loc3 = new Location("Berlin");
    }
}
