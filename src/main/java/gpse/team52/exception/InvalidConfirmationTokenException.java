package gpse.team52.exception;

/**
 * Thrown if the User email validation token is invalid.
 */
public class InvalidConfirmationTokenException extends Exception {
    public InvalidConfirmationTokenException(final String msg) {
        super(msg);
    }
}
