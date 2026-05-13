package com.flex.tender.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;

public interface OfferRepository {

    Offer save(Offer offer);
    
    void update(Offer offer);
    
    Set<Offer> findByBidderWithPagination(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> findByContractorWithPagination(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> findByTenderWithPagination(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countByBidder(Integer bidderId);
    
    Integer countByContractor(Integer contractorId);
    
    Integer countAllByTender(Integer tenderId);
    
    Map<Integer, Integer> countByTenderIdIn(List<Integer> tenderIds);
    
    List<Offer> findByBidderIdAndTenderIdIn(Integer bidderId, List<Integer> tenderIds);
        
    Offer findById(Integer offerId);

    List<Offer> findByTenderIdAndGlobalStatusIn(Integer id, List<EOfferStatus> statuses);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);

}