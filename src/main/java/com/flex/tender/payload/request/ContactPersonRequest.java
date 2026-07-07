package com.flex.tender.payload.request;

public record ContactPersonRequest(
        String firstName, 
        String lastName, 
        String phoneNumber) {
}