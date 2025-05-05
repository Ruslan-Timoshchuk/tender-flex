package pl.com.tenderflex.payload.request;

public record OfferSubmissionRequest(
        Integer tenderId, 
        OfferRequest offer) {
}