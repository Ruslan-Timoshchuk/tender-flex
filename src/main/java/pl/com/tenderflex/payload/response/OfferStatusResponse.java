package pl.com.tenderflex.payload.response;

import pl.com.tenderflex.model.enums.EOfferStatus;

public record OfferStatusResponse(
        Integer offerId, 
        EOfferStatus status) {
}