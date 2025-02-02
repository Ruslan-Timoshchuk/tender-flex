package pl.com.tenderflex.payload.response;

public record CpvResponse(
        Integer id, 
        String code, 
        String description) {
}