package com.flex.tender.payload.response;

import com.flex.tender.model.enumeration.EOfferStatus;

public record OfferStatusResponse(
        Integer offerId, 
        EOfferStatus status) {
}