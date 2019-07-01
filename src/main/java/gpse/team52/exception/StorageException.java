package gpse.team52.exception;

/**
 * StorageException.
 */
public class StorageException extends RuntimeException {

    private static final long serialVersionUID = 4193826755120725201L;

    public StorageException(final String message) {
        super(message);
    }

    public StorageException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
