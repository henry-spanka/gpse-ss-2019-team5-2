package gpse.team52.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.MeetingRoom;
import gpse.team52.domain.Participant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Meeting Repository.
 */
public interface MeetingRepository extends CrudRepository<Meeting, UUID> {

    Iterable<Meeting> findByStartAtBetweenAndParticipantsIn(LocalDateTime start, LocalDateTime end, Iterable<Participant>meetingpart);

    Iterable<Meeting> findByOrderByStartAtAsc();

    @Query("SELECT r FROM Meeting m JOIN m.rooms r WHERE m.startAt <= :endAt AND m.endAt >= :startAt")
    List<MeetingRoom> getMeetingRoomMappingInTimeFrame(@Param("startAt") LocalDateTime startAt,
                                                       @Param("endAt") LocalDateTime endAt);
}
