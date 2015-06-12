package kata.exception;

public class RoleGrantException extends Exception {

    private static final long serialVersionUID = -4270167729315162759L;

    public RoleGrantException(String message) {
        super(message);
    }

    public RoleGrantException() {
        super();
    }

}
