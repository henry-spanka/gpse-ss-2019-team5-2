package gpse.team52.exception;

/**
 * Thrown if the meeting doesn't interfere with the new meeting and doesn't need to be rebooked.
 */
public class RebookingNotNecessaryException extends Exception {
    private static final long serialVersionUID = -3326365917244380689L;

    public RebookingNotNecessaryException(final String msg) {
        super(msg);
    }
}
