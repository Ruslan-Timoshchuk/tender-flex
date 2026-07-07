package com.flex.tender.exception;

@SuppressWarnings("serial")
public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(String message) {
        super(message);
    }  
    
}