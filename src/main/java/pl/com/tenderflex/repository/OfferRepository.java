package pl.com.tenderflex.repository;

import java.util.Optional;
import java.util.Set;

import pl.com.tenderflex.model.Offer;

public interface OfferRepository {

    Offer save(Offer offer);
    
    Set<Offer> findByBidderWithPagination(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> findByContractorWithPagination(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countOffersByBidder(Integer bidderId);
    
    Integer countOffersByContractor(Integer contractorId);
    
    Integer countOffersByTender(Integer tenderId);
        
    Offer findById(Integer offerId);

    Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId);

    Set<Offer> findByTender(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip);

}