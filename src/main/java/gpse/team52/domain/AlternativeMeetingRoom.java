package gpse.team52.domain;

import java.util.List;
import java.util.Map;

/**
 * AlternativeMeetingRoom class.
 */
public class AlternativeMeetingRoom {

    private Meeting meeting;
    private Map<String, List<Room>> alternatives;

    public AlternativeMeetingRoom(final Meeting m, final Map<String, List<Room>> alter) {
        meeting = m;
        alternatives = alter;
    }

    public Map<String, List<Room>> getAlternatives() {
        return alternatives;
    }

    public Meeting getMeeting() {
        return meeting;
    }
}
