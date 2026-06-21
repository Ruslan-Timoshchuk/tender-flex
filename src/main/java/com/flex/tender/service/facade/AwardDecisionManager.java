package com.flex.tender.service.facade;

import com.flex.tender.payload.request.AwardOfferDecisionRequest;

public interface AwardDecisionManager {

    void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest);

}
