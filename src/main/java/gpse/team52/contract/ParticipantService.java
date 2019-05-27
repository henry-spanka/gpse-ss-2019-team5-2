package gpse.team52.contract;

<<<<<<< HEAD
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.stereotype.Service;

=======
>>>>>>> develop
import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Participant;

public interface ParticipantService {

    List<Participant> getAllParticipants();

<<<<<<< HEAD
    Iterable<Participant> findByUser(User user);
=======
    void deleteById(UUID id);
>>>>>>> develop
}
