package pl.com.tenderflex.exception;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import java.io.IOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.amazonaws.AmazonServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ApiError handleBadCredentialsException(BadCredentialsException exception) {
        return new ApiError(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage(),
                "Incorrect authentication data");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        return new ApiError(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(), "Resource is not exists");
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleDataAccessException(DataAccessException exception) {
        return new ApiError(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(), "Dao error occured");
    }
    
    @ExceptionHandler(CookiesNotPresentException.class)
    @ResponseStatus(UNAUTHORIZED)
    public ApiError handleCookiesNotPresentException(CookiesNotPresentException exception) {
        return new ApiError(now(), UNAUTHORIZED.value(), UNAUTHORIZED, exception.getMessage(), "Cookies error occured");
    }

    @ExceptionHandler(FileNotExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleFileNotExistsException(FileNotExistsException exception) {
        return new ApiError(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the file");
    }
    
    @ExceptionHandler(IOException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleIOException(IOException exception) {
        return new ApiError(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the document");
    }
    
    @ExceptionHandler(AmazonServiceException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiError handleAmazonServiceException(AmazonServiceException exception) {
        return new ApiError(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage(),
                "Error occurred when uploading the document to the backet");
    }
}