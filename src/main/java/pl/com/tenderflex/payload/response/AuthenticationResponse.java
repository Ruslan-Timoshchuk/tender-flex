package pl.com.tenderflex.payload.response;

public record AuthenticationResponse(
        Integer userId, 
        String role) {
}