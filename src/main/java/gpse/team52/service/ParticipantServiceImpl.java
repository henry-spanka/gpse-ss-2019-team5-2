package gpse.team52.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gpse.team52.contract.ParticipantService;
import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import gpse.team52.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;

    @Autowired
    public ParticipantServiceImpl(final ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public List<Participant> getAllParticipants() {
        final List<Participant> participants = new ArrayList<>();

        participantRepository.findAll().forEach(participants::add);

        return participants;
    }

    @Override
    public Iterable<Participant> findByUser(User user) {
        return participantRepository.findByUser(user);
    }

    @Override
    public void deleteById(final UUID id) {
        participantRepository.deleteById(id);
    }
}
