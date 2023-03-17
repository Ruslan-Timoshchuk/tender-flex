package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Offer;

public interface OfferRepository {

    Offer create(Offer offer, Integer bidderId);
    
    List<Offer> getByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countOffersByBidder(Integer bidderId);
    
    Offer getById(Integer offerId);

}