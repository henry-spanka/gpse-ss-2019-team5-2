package gpse.team52.exception;

/**
 * Exception is thrown if the email is not found.
 */
public class EmailNotFoundException extends Exception {
    private static final long serialVersionUID = -1445655293143389741L;

    public EmailNotFoundException(final String msg) {
        super(msg);
    }
}
