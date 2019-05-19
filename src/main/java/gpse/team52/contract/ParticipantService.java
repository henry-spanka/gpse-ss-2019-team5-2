package gpse.team52.contract;

import gpse.team52.domain.Participant;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    List<Participant> getAllParticipants();
}
