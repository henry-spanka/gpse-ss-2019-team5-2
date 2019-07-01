package gpse.team52.exception;

/**
 * Thrown if the meeting doesn't interfere with the new meeting and doesn't need to be rebooked.
 */
public class RebookingNotNecessaryException extends Exception {
    public RebookingNotNecessaryException(final String msg) {
        super(msg);
    }
}
