package pl.com.tenderflex.payload.request;

public record ContactPersonRequest(
        String firstName, 
        String lastName, 
        String phoneNumber) {
}