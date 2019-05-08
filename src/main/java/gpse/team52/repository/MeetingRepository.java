package gpse.team52.repository;

import gpse.team52.domain.Meeting;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Meeting Repository.
 */
public interface MeetingRepository extends CrudRepository<Meeting, UUID> {
}
