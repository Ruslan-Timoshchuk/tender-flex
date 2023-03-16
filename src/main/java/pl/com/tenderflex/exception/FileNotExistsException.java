package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class FileNotExistsException extends RuntimeException {

    public FileNotExistsException(String message) {
        super(message);
    }  
}