package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class DataMappingException extends RuntimeException {

    public DataMappingException(String message, Throwable cause) {
        super(message, cause);
    }   
}