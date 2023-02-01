package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.Offer;

public interface OfferRepository {

    Offer create(Offer offer);
    
}