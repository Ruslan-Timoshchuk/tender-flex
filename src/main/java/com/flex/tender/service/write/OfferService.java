package com.flex.tender.service.write;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.embedded.PrincipalSummary;
import com.flex.tender.payload.request.OfferRequest;
import com.flex.tender.payload.response.OfferSummaryResponse;

public interface OfferService {

    Offer buildEntity(PrincipalSummary principalSummary, OfferRequest offerRequest);
    
    OfferSummaryResponse save(Offer offer);

    Offer applyAwardDecision(Offer offer, AwardDecision awardDecision);

    Offer applyRejectDecision(Offer offer, RejectDecision rejectDecision);

    Offer rejectUnsuitableOffers(Offer winningOffer, RejectDecision rejectDecision);
    
    Offer handleOnContractDecline(Offer offer);
    
    Offer handleOnContractApprove(Offer offer);

    Offer handleOnSigningDeadlinePassed(Offer offer);
    
}