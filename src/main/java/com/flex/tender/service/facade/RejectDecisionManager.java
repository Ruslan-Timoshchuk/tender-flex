package com.flex.tender.service.facade;

import com.flex.tender.payload.request.RejectOfferDecisionRequest;

public interface RejectDecisionManager {

    void applyRejectDecision(RejectOfferDecisionRequest rejectOfferDecisionRequest);

}