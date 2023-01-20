package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}