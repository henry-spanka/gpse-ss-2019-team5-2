package gpse.team52.service;

import gpse.team52.contract.PositionService;
import gpse.team52.domain.Position;
import gpse.team52.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Autowired
    public PositionServiceImpl(final PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Override
    public Position update(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position createPosition(final String positionName, final List rights) {
        final Position position = new Position(positionName, rights);

        return positionRepository.save(position);
    }

    @Override
    public Iterable<Position> getAllPositions() {
        return positionRepository.findAll();
    }

    @Override
    public Optional<Position> getPosition(UUID positionID) {
        return positionRepository.findById(positionID);
    }
}
