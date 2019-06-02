package gpse.team52.contract;

import gpse.team52.domain.Location;

public interface LocationService {

    Location createLocation(String name);

    Location createLocation(Location location);

    Iterable<Location> getAllLocations();
}
