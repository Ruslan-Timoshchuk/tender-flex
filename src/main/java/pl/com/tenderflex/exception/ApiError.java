package pl.com.tenderflex.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final Integer code;
    private final HttpStatus status;
    private final String message;
    private final String cause;
   
    public ApiError(HttpStatus status, Throwable exception, Throwable cause) {
        this.timestamp = LocalDateTime.now();
        this.code = status.value();
        this.status = status;
        this.message = exception.getMessage();
        this.cause = cause.getMessage();
    }
    
    public Integer getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }

    public String getCause() {
        return cause;
    }    
}