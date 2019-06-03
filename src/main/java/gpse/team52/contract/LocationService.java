package gpse.team52.contract;

import gpse.team52.domain.Location;

public interface LocationService {

    Location createLocation(String name);

    Location createLocation(Location location);

    Location createLocation(String name, int time);

    Iterable<Location> getAllLocations();
}
