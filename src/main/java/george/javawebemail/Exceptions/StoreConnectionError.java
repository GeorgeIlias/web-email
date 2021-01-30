package george.javawebemail.Exceptions;

public class StoreConnectionError extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -6559879413433812148L;

    public StoreConnectionError() {

    }

    public StoreConnectionError(String message) {
        super(message);
    }

}
