package gpse.team52.repository;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.Participant;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Meeting Repository.
 */
public interface MeetingRepository extends CrudRepository<Meeting, UUID> {

    Iterable<Meeting> findByStartAtBetween(LocalDateTime start, LocalDateTime end);

}
