package pl.com.tenderflex.payload.iresponse.response;

import pl.com.tenderflex.model.enums.EOfferStatus;

public record OfferStatusResponse (EOfferStatus offerStatus) {}