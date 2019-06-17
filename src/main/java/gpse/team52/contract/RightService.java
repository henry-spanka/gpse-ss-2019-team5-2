package gpse.team52.contract;

import gpse.team52.domain.Right;

public interface RightService {
    Right createRight(String rightName);
    Right update(Right right);


    Iterable<Right> getAllRights();
}
