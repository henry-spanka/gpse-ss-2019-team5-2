package gpse.team52.contract;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {

    List<Participant> getAllParticipants();

    Iterable<Participant> findByUser(User user);
}
