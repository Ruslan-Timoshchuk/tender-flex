package com.flex.tender.service.transactional;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;

public interface AwardDecisionService {

    AwardDecision buildEntity(AwardDecisionRequest awardDecisionRequest);
    
    AwardDecision save(AwardDecision awardDecision);

    void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest);

}