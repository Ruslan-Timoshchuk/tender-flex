package com.flex.tender.service.write;

import java.util.List;
import java.util.Map;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferCountResponse;
import com.flex.tender.payload.response.OfferDetailsResponse;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferService {

    Offer buildEntity(PrincipalSummary principalSummary, OfferRequest offerRequest);
    
    OfferSummaryResponse save(Offer offer);

    boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses);
    
    OfferDetailsResponse findDetailsById(Integer offerId);

    OfferCountResponse countByBidder(Integer bidderId);
    
    OfferCountResponse countByContractor(Integer contractorId);
    
    Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer userId, List<Integer> tenderIds);

    Offer applyAwardDecision(Offer offer, AwardDecision awardDecision);

    Offer applyRejectDecision(Offer offer, RejectDecision rejectDecision);

    Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision);
    
    Offer handleOnContractDecline(Offer offer);

    Offer handleOnSigningDeadlinePassed(Offer offer);

    Map<Integer, Integer> countOffersByTenderIds(List<Integer> tenderIds);

}