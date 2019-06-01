package gpse.team52.contract;

import gpse.team52.domain.Participant;
import gpse.team52.domain.User;


import java.util.List;
import java.util.UUID;


/**
 * Participant service interface.
 */
public interface ParticipantService {

    List<Participant> getAllParticipants();

    Iterable<Participant> findByUser(User user);

    void deleteById(UUID id);
}
