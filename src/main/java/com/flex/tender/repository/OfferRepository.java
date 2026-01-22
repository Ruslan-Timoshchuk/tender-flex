package com.flex.tender.repository;

import java.util.Optional;
import java.util.Set;

import com.flex.tender.model.Offer;

public interface OfferRepository {

    Offer save(Offer offer);
    
    void update(Offer offer);
    
    Set<Offer> findByBidderWithPagination(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> findByContractorWithPagination(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> findByTenderWithPagination(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countByBidder(Integer bidderId);
    
    Integer countByContractor(Integer contractorId);
    
    Integer countOffersByTender(Integer tenderId);
        
    Offer findById(Integer offerId);

    Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId);

    Set<Offer> findAllByTender(Integer id);

}