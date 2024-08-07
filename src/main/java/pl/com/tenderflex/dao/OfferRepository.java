package pl.com.tenderflex.dao;

import java.util.Optional;
import java.util.Set;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Total;

public interface OfferRepository {

    Offer create(Offer offer);
    
    Set<Offer> getPageByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Set<Offer> getPageByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countOffersByBidder(Integer bidderId);
    
    Integer countOffersByContractor(Integer contractorId);
    
    Integer countOffersByTender(Integer tenderId);
    
    Integer countActiveOffersByTender(Integer tenderId, Integer activeOfferStatusId);
    
    Offer getById(Integer offerId);
    
    Total getTotalTendersAndOffersByBidder(Integer bidderId);

    void addAwardDecision(String awardDecision, Integer stageStatus, Integer offerId);

    void addRejectDecision(String rejectDecision, Integer stageStatus, Integer offerId);

    void updateOfferStatus(Integer stageStatus, Integer offerId);

    void  updateOffersStatus(Integer statusId, String rejectDecisionName, Integer tenderId,
            Integer statusOfActiveOffers, Integer offerId);

    Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId);

    Set<Offer> getByTender(Integer tenderId);

}