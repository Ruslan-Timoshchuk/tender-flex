package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class CookiesNotPresentException extends RuntimeException {

    public CookiesNotPresentException(String message) {
        super(message);
    }  
}