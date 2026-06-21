package com.flex.tender.service.write;

import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.request.RejectOfferDecisionRequest;

public interface RejectDecisionService {

    RejectDecision buildEntity(RejectDecisionRequest rejectDecisionRequest);
    
    RejectDecision save(RejectDecision rejectDecision);

    void applyRejectDecision(RejectOfferDecisionRequest rejectOfferDecisionRequest);

}