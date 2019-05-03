package gpse.team52.exception;

/**
 * Thrown if the username already exists in the database.
 */
public class UsernameExistsException extends Exception {
    private static final long serialVersionUID = 1154763382310277182L;

    public UsernameExistsException(final String msg) {
        super(msg);
    }
}
