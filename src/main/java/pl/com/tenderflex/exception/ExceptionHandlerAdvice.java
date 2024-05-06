package pl.com.tenderflex.exception;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import java.io.IOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.amazonaws.AmazonServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleBadCredentialsException(BadCredentialsException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage(),
                "Incorrect authentication data");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(), "Resource is not exists");
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleDataAccessException(DataAccessException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(), "Dao error occured");
    }
    
    @ExceptionHandler(CookiesNotPresentException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ExceptionHandlerResponse handleCookiesNotPresentException(CookiesNotPresentException exception) {
        return new ExceptionHandlerResponse(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage(), "Cookies error occured");
    }

    @ExceptionHandler(FileNotExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleFileNotExistsException(FileNotExistsException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the file");
    }
    
    @ExceptionHandler(IOException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleIOException(IOException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the document");
    }
    
    @ExceptionHandler(AmazonServiceException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleAmazonServiceException(AmazonServiceException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the document to the backet");
    }
    
    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleMissingServletRequestPartException(
            MissingServletRequestPartException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Requested file-part is doesn't present");
    }
}