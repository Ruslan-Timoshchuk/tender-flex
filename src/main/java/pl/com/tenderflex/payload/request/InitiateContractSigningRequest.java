package pl.com.tenderflex.payload.request;

public record InitiateContractSigningRequest(
        Integer contractId, 
        Integer offerId, 
        Integer awardId) {
}