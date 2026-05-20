package com.flex.tender.service;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;

public interface AwardDecisionService {

    AwardDecision findById(Integer id);

    AwardDecisionResponse save(AwardDecisionRequest awardDecisionRequest);
    
}