package com.flex.tender.payload.response;

public record OfferRejectionResponse(
        Integer offerId,
        String offerStatus) {
}