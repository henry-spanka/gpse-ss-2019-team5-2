package gpse.team52.exception;

/**
 * Thrown if no room is available at the specified time.
 */
public class NoRoomAvailableException extends Exception {
    private static final long serialVersionUID = -2754791135607580709L;

    public NoRoomAvailableException(final String msg) {
        super(msg);
    }

    public NoRoomAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
