package gpse.team52.contract;

import gpse.team52.domain.Right;

public interface RightService {
    Right createRight(String rightName);

    Iterable<Right> getAllRights();
}
