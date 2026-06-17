package com.flex.tender.service.transactional;

import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.request.RejectOfferDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;

public interface RejectDecisionService {

    RejectDecisionResponse save(RejectDecisionRequest rejectDecisionRequest);

    void applyRejectDecision(RejectOfferDecisionRequest rejectOfferDecisionRequest);

}