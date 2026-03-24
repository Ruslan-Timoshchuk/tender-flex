package com.flex.tender.payload.request;

public record AwardOfferRequest(
        Integer contractId, 
        Integer offerId, 
        Integer awardId) {
}