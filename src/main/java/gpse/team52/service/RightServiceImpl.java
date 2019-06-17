package gpse.team52.service;

import gpse.team52.contract.RightService;
import gpse.team52.domain.Position;
import gpse.team52.domain.Right;
import gpse.team52.repository.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RightServiceImpl implements RightService {
    @Autowired
    private RightRepository rightRepository;

    @Override
    public Right createRight(String rightName) {
        Right right = new Right(rightName);
        return rightRepository.save(right);
    }
    public Right update(final Right right) {
        return rightRepository.save(right);
    }

    @Override
    public Iterable<Right> getAllRights() {
        return rightRepository.findAll();
    }
}
