package gpse.team52.exception;

/**
 * Thrown if the User email validation token is invalid.
 */
public class InvalidConfirmationTokenException extends Exception {
    private static final long serialVersionUID = -3379769333660508995L;

    public InvalidConfirmationTokenException(final String msg) {
        super(msg);
    }
}
