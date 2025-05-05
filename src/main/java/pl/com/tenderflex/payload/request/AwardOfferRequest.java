package pl.com.tenderflex.payload.request;

public record AwardOfferRequest(
        Integer contractId, 
        Integer offerId, 
        Integer awardId) {
}