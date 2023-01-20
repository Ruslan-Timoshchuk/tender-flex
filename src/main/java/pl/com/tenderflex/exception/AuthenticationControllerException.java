package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class AuthenticationControllerException extends RuntimeException {

    public AuthenticationControllerException(String message) {
        super(message);
    }
}