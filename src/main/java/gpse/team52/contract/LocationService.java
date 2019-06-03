package gpse.team52.contract;

import gpse.team52.domain.Location;

import java.util.Optional;

public interface LocationService {

    Location createLocation(String name);

    Location createLocation(Location location);

    Iterable<Location> getAllLocations();

    Optional<Location> getLocation(String name);
}
