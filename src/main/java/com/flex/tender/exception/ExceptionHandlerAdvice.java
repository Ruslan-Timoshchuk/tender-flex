package com.flex.tender.exception;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;
import java.io.IOException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import com.amazonaws.AmazonServiceException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(FileNotExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleFileNotExistsException(FileNotExistsException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleIOException(IOException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AmazonServiceException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleAmazonServiceException(AmazonServiceException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionHandlerResponse handleMissingServletRequestPartException(
            MissingServletRequestPartException exception) {
        return new ExceptionHandlerResponse(now(), BAD_REQUEST.value(), BAD_REQUEST, exception.getMessage());
    }

}