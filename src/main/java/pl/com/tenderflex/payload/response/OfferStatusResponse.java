package pl.com.tenderflex.payload.response;

import pl.com.tenderflex.model.OfferStatus;

public record OfferStatusResponse(
        Integer offerId, 
        OfferStatus status) {
}