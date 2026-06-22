package com.flex.tender.service.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.request.RejectOfferDecisionRequest;
import com.flex.tender.service.facade.RejectDecisionManager;
import com.flex.tender.service.read.OfferDetailsService;
import com.flex.tender.service.read.RejectDecisionDetailsService;
import com.flex.tender.service.write.OfferService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RejectDecisionManagerImpl implements RejectDecisionManager {

    private final RejectDecisionDetailsService rejectDecisionDetailsService;
    private final OfferDetailsService offerDetailsService;
    private final OfferService offerService;
    
    @Override
    public void applyRejectDecision(RejectOfferDecisionRequest rejectOfferDecisionRequest) {
        RejectDecision rejectDecision = rejectDecisionDetailsService.findById(rejectOfferDecisionRequest.rejectDecisionId());
        Offer offer = offerDetailsService.findById(rejectOfferDecisionRequest.offerId());
        offerService.applyRejectDecision(offer, rejectDecision);
    }
    
}