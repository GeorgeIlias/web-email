package george.javawebemail.Exceptions;

public class NoDatabaseObject extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 4977944188049255738L;

    public NoDatabaseObject() {
    }

    public NoDatabaseObject(String message) {
        super(message);
    }

}
