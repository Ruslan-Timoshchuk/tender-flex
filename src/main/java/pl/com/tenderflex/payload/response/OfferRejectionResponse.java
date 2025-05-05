package pl.com.tenderflex.payload.response;

public record OfferRejectionResponse(
        Integer offerId,
        String offerStatus) {
}