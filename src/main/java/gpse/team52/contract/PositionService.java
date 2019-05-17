package gpse.team52.contract;

import gpse.team52.domain.Position;

import java.util.Optional;
import java.util.UUID;

public interface PositionService{
    Position update(Position position);

    Position createPosition(String positionName, String rights);

    Iterable<Position> getAllPositions();
    Optional<Position> getPosition(UUID positionID);
}
