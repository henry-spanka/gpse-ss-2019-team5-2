package gpse.team52.exception;

/**
 * Thrown if no other room is available at all specified locations with the needed equipment
 * or the time limit is exceeded.
 */
public class RebookingImpossibleException extends Exception {

    private static final long serialVersionUID = -8628796949508911883L;

    public  RebookingImpossibleException(final String msg) {
        super(msg);
    }
}
