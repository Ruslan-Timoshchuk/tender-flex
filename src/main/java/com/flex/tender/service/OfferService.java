package com.flex.tender.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferStatusResponse;

public interface OfferService {

    Offer save(Tender tender, Offer offer);

    Set<Offer> findAllByTender(Integer tenderId);
    
    Offer findById(Integer offerId);
    
    OfferResponse findDetailsByBidder(Integer offerId); 
    
    OfferResponse findDetailsByContractor(Integer offerId); 
    
    Page<OfferResponse> findPageByBidder(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByContractor(Integer contractorId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferResponse> findPageByTender(Integer tenderId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities);

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

    Offer handleOnSigningDeadlinePassed(Offer offer);

}