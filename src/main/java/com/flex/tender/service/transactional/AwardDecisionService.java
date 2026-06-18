package com.flex.tender.service.transactional;

import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;

public interface AwardDecisionService {

    AwardDecisionResponse save(AwardDecisionRequest awardDecisionRequest);

    void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest);
    
}