package gpse.team52.repository;

import gpse.team52.domain.Meeting;
import org.springframework.data.repository.CrudRepository;

/**
 * Meeting Repository.
 */
public interface MeetingRepository extends CrudRepository<Meeting, Long> {
}
