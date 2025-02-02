package pl.com.tenderflex.payload.response;

public record ContactPersonResponse(
        String firstName, 
        String lastName, 
        String phoneNumber) {
}