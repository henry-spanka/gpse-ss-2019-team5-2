package gpse.team52.repository;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Meeting Repository.
 */
public interface MeetingRepository extends CrudRepository<Meeting, UUID> {

    default Iterable<Meeting> fByStartAt(LocalDate today, User userid) {
        return findByStartAtBetweenAndParticipants(today.atStartOfDay(), today.plusDays(1).atStartOfDay(), userid);
    }

    Iterable<Meeting> findByStartAtBetweenAndParticipants(LocalDateTime start, LocalDateTime end, User userid);
    //TODO Fix Query to obtain Meetings where User is Participant and specific Date.

}
