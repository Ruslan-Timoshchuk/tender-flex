package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.request.OfferDetailsRequest;

public interface OfferService {

    void createOffer(OfferDetailsRequest offer, Integer bidderId);

}