package com.flex.tender.service.write;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.payload.request.AwardDecisionRequest;

public interface AwardDecisionService {

    AwardDecision buildEntity(AwardDecisionRequest awardDecisionRequest);
    
    AwardDecision save(AwardDecision awardDecision);

}