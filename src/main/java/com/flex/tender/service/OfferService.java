package com.flex.tender.service;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.SummaryPage;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferService {

    OfferSummaryResponse save(PrincipalSummary principalSummary, OfferRequest offerRequest);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);
    
    Offer findById(Integer offerId);
    
    OfferDetailsResponse findDetailsById(Integer offerId);
    
    SummaryPage<OfferSummaryResponse> findByBidderWithPagination(Integer bidderId, Integer currentPage, Integer offersPerPage);
    
    SummaryPage<OfferSummaryResponse> findByContractorWithPagination(Integer contractorId, Integer currentPage, Integer offersPerPage);
    
    SummaryPage<OfferSummaryResponse> findByTenderWithPagination(Integer tenderId, Integer currentPage, Integer offersPerPage);

    OfferCountResponse countByBidder(Integer bidderId);
    
    OfferCountResponse countByContractor(Integer contractorId);
    
    Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds);

    Offer selectWinningOffer(Offer offer, AwardDecision awardDecision);

    Offer rejectOffer(Offer offer, RejectDecision rejectDecision);

    Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision);
    
    Offer handleOnContractDecline(Offer offer);

    boolean hasContract(Offer offer);

    Offer handleOnSigningDeadlinePassed(Offer offer);

    Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds);

}