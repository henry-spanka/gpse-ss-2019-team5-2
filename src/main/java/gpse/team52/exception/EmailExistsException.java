package gpse.team52.exception;

/**
 * Exception is thrown if the email already exists in the database.
 */
public class EmailExistsException extends Exception {
    private static final long serialVersionUID = -3851175243583138354L;

    public EmailExistsException(final String msg) {
        super(msg);
    }
}
