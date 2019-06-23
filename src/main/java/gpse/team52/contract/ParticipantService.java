package gpse.team52.contract;

import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Participant;
import gpse.team52.domain.User;


/**
 * Participant service interface.
 */
public interface ParticipantService {

    List<Participant> getAllParticipants();

    Iterable<Participant> findByUser(User user);

    void deleteById(UUID id);
}
