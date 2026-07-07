package com.flex.tender.exception;

@SuppressWarnings("serial")
public class FileNotExistsException extends RuntimeException {

    public FileNotExistsException(String message) {
        super(message);
    }  
}