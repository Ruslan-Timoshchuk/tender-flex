package pl.com.tenderflex.payload.request;

public record OfferRejectionRequest(
        Integer offerId,
        Integer rejectId) {
}