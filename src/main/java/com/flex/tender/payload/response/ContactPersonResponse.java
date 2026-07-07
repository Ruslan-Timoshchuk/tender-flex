package com.flex.tender.payload.response;

/**
 * @author Ruslan Tymoshchuk
 */
public record ContactPersonResponse(
        String firstName, 
        String lastName, 
        String phoneNumber) {
}