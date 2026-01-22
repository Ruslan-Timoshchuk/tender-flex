package com.flex.tender.payload.request;

public record OfferSubmissionRequest(
        Integer tenderId, 
        OfferRequest offer) {
}