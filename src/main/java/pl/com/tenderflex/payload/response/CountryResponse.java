package pl.com.tenderflex.payload.response;

public record CountryResponse(
        Integer id, 
        String name,
        String isoCode,
        String phoneCode) {
}