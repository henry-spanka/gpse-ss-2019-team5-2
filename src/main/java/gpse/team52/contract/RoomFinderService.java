package gpse.team52.contract;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import gpse.team52.domain.Room;
import gpse.team52.form.MeetingCreationForm;

public interface RoomFinderService {
    HashMap<String, List<Room>> find(MeetingCreationForm meetingCreationForm);
}
