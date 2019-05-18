package gpse.team52.service;

import gpse.team52.contract.ParticipantService;
import gpse.team52.domain.Participant;
import gpse.team52.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public Participant getParticipant(UUID userID) {
        participantRepository.findById(userID);
    }
}
