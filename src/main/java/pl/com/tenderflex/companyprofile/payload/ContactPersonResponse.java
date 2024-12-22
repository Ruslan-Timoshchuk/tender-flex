package pl.com.tenderflex.companyprofile.payload;

public record ContactPersonResponse(
        String firstName, 
        String lastName, 
        String phoneNumber) {
}