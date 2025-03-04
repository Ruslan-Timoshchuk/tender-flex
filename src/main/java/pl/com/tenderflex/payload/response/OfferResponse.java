package pl.com.tenderflex.payload.response;

public record OfferResponse(
        Integer id, 
        Integer companyProfileId, 
        Integer price, 
        String date, 
        String status) {
}