package kata.exception;


public class AuthorizationException extends Exception  {

    private static final long serialVersionUID = 7820090165472094524L;
    
    public AuthorizationException(String message) {
        super(message);
    }
    
    public AuthorizationException() {
        super();
    }

}
