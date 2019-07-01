package gpse.team52.repository;

import java.net.URL;
import java.util.Optional;

/**
 * URL repository.
 */
public interface URLRepository {
    Optional<URL> findByUsername(String URL);
}

