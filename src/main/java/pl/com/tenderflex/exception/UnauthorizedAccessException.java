package pl.com.tenderflex.exception;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(FORBIDDEN)
public class UnauthorizedAccessException extends RuntimeException {
    
    public UnauthorizedAccessException(String message) {
        super(message);
    }
    
}