package gpse.team52.repository;

import java.util.UUID;

import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Participant repository.
 */
public interface ParticipantRepository extends CrudRepository<Participant, UUID> {
    Iterable<Participant> findByUser(User user);
}
