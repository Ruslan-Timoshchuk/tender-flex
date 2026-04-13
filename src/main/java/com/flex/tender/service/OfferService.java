package com.flex.tender.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.Page;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferService {

    Offer save(Tender tender, Offer offer);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);
    
    Offer findById(Integer offerId);
    
    OfferResponse findDetailsByBidder(Integer offerId); 
    
    OfferResponse findDetailsByContractor(Integer offerId); 
    
    Page<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer currentPage, Integer offersPerPage);
    
    Page<OfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByUserAuthority(Integer userId, Collection<? extends GrantedAuthority> authorities);
    
    List<Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds);

    Offer selectWinningOffer(Offer offer, AwardDecision awardDecision);

    Offer rejectOffer(Offer offer, RejectDecision rejectDecision);

    Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision);
    
    Offer handleOnContractDecline(Offer offer);

    boolean hasContract(Offer offer);

    Offer handleOnSigningDeadlinePassed(Offer offer);

    Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds);

}