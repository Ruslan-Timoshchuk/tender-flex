package pl.com.tenderflex.payload.response;

public record AwardResultResponse(
        Integer contractId, 
        Integer awardId,
        Integer offerId,
        String offerStatus) {
}