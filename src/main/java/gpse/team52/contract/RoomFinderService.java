package gpse.team52.contract;

import java.util.List;
import java.util.Map;

import gpse.team52.domain.Meeting;
import gpse.team52.domain.Room;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.exception.RebookingImpossibleException;
import gpse.team52.exception.RebookingNotNecessaryException;
import gpse.team52.form.MeetingCreationForm;

/**
 * Interface for the room finder.
 */
public interface RoomFinderService {
    Map<String, List<Room>> find(MeetingCreationForm meetingCreationForm);

    List<Room> findBest(MeetingCreationForm meetingCreationForm) throws NoRoomAvailableException;

    Map<String, List<Room>> findOther(Meeting meeting, Map<String, List<Room>> roomsForNew)
    throws RebookingNotNecessaryException, RebookingImpossibleException;
}
