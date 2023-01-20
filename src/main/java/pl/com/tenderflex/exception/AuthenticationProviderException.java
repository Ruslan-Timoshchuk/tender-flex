package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class AuthenticationProviderException extends RuntimeException {

    public AuthenticationProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}