package gpse.team52.contract;

import gpse.team52.domain.Location;

public interface LocationService {

    Location createLocation(String name);

    Location createLocation(Location location);

    Location createLocation(String name, long time);

    Iterable<Location> getAllLocations();
}
