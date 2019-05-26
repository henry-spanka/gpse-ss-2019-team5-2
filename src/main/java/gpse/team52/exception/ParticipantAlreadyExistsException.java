package gpse.team52.exception;

/**
 * Thrown if the participant is already participating in the meeting.
 */
public class ParticipantAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 5581885295278703497L;

    public ParticipantAlreadyExistsException(String message) {
        super(message);
    }
}
