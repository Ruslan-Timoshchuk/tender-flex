package com.flex.tender.payload.request;

public record OfferRejectionRequest(
        Integer offerId,
        Integer rejectId) {
}