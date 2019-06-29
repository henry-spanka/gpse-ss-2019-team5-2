package gpse.team52.exception;

/**
 * Thrown if no other room is available at all specified locations with the needed equipment
 * or the time limit is exceeded.
 */
public class RebookingImpossibleException extends Exception {

    public  RebookingImpossibleException(final String msg) {
        super(msg);
    }
}
