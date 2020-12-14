/**
 * 
 */

package george.javawebemail.Exceptions;

public class IncorrectDatabaseResponse extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public IncorrectDatabaseResponse(String message) {
        super(message);
    }

    public IncorrectDatabaseResponse() {
        super();
    }

}
