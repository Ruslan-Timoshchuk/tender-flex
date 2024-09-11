package pl.com.tenderflex.exception;

@SuppressWarnings("serial")
public class FileUploadException extends RuntimeException {

    public FileUploadException(String message, Throwable cause) {
        super(message);
    }
    
}