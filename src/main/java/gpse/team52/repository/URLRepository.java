package gpse.team52.repository;

import gpse.team52.domain.User;

import java.net.URL;
import java.util.Optional;

public interface URLRepository {
    Optional<URL> findByUsername(String URL);
}

