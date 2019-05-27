package gpse.team52.contract;

import java.util.List;
import java.util.Map;

import gpse.team52.domain.Room;
import gpse.team52.exception.NoRoomAvailableException;
import gpse.team52.form.MeetingCreationForm;

/**
 * Interface for the room finder.
 */
public interface RoomFinderService {
    Map<String, List<Room>> find(MeetingCreationForm meetingCreationForm);

    List<Room> findBest(MeetingCreationForm meetingCreationForm) throws NoRoomAvailableException;
}
