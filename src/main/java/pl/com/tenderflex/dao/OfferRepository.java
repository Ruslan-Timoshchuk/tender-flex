package pl.com.tenderflex.dao;

import java.util.List;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Total;

public interface OfferRepository {

    Offer create(Offer offer, Integer bidderId);
    
    List<Offer> getByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip);
    
    List<Offer> getByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip);
    
    List<Offer> getByTender(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip);
    
    Integer countOffersByBidder(Integer bidderId);
    
    Integer countOffersByContractor(Integer contractorId);
    
    Integer countOffersByTender(Integer tenderId);
    
    Integer countActiveOffersByTender(Integer tenderId, Integer activeOfferStatusId);
    
    Offer getById(Integer offerId);

    boolean isExistsOfferByTenderAndBidder(Integer tenderId, Integer bidderId);
    
    Total getTotalTendersAndOffersByBidder(Integer bidderId);

    void addAwardDecision(String awardDecision, Integer stageStatus, Integer offerId);

    void addRejectDecision(String rejectDecision, Integer stageStatus, Integer offerId);

    void updateOfferStatus(Integer stageStatus, Integer offerId);

    void  updateOffersStatus(Integer statusId, String rejectDecisionName, Integer tenderId,
            Integer statusOfActiveOffers, Integer offerId);

}