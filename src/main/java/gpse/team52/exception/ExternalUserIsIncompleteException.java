package gpse.team52.exception;

/**
 * Thrown if the external user has incomplete fields.
 */
public class ExternalUserIsIncompleteException extends Exception {
    private static final long serialVersionUID = 7212517322006652166L;

    public ExternalUserIsIncompleteException(final String message) {
        super(message);
    }
}
