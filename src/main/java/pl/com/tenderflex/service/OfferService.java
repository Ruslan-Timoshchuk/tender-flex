package pl.com.tenderflex.service;

import java.util.Optional;
import java.util.Set;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.payload.Page;
import pl.com.tenderflex.payload.response.OfferCountResponse;
import pl.com.tenderflex.payload.response.OfferResponse;
import pl.com.tenderflex.payload.response.OfferStatusResponse;

public interface OfferService {

    Offer save(Tender tender, Offer offer);

    Set<Offer> findAllByTender(Integer tenderId);
    
    Offer findById(Integer offerId);
    
    OfferResponse findDetailsById(Integer offerId);
    
    Page<OfferResponse> findPageByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByTender(Integer tenderId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByBidder(Integer bidderId);
    
    OfferCountResponse countByContractor(Integer contractorId);

    OfferCountResponse countByTender(Integer tenderId);
    
    Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer userId);

    OfferStatusResponse checkOfferStatus(Integer tenderId, Integer userId);

    Offer selectWinningOffer(Offer offer, AwardDecision awardDecision);

    Offer rejectOffer(Offer offer, RejectDecision rejectDecision);

    Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision);
    
    Offer handleOnContractDecline(Offer offer);

    boolean hasAwardDecision(Offer offer);

    boolean hasContract(Offer offer);

    boolean hasRejectDecision(Offer offer);  

}