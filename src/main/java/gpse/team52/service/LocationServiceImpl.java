package gpse.team52.service;

import java.util.Optional;

import gpse.team52.contract.LocationService;
import gpse.team52.domain.Location;
import gpse.team52.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LocationService implementation.
 */
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository repository;

    @Autowired
    public LocationServiceImpl(final LocationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Location createLocation(String name) {
        return createLocation(name, 0);
    }

    @Override
    public Location createLocation(String name, long time) {
        final Location location = new Location(name);
        location.setTimeoffset(time);
        return createLocation(location);
    }

    @Override
    public Location createLocation(Location location) {
        return repository.save(location);
    }

    @Override
    public Iterable<Location> getAllLocations() {
        return repository.findAll();
    }

    @Override
    public Optional<Location> getLocation(final String name) {
        return repository.findByName(name);
    }

}
