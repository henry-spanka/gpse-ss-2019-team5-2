package gpse.team52.exception;

public class NoRoomAvailableException extends Exception {
    private static final long serialVersionUID = -2754791135607580709L;

    public NoRoomAvailableException(final String msg) {
        super(msg);
    }
}
