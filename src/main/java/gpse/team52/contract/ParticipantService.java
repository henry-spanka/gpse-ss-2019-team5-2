package gpse.team52.contract;

import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Participant;

public interface ParticipantService {

    List<Participant> getAllParticipants();

    void deleteById(UUID id);
}
