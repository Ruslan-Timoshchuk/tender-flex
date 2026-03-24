package com.flex.tender.service;

import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Tender;

public interface AwardDecisionService {

    AwardDecision save(AwardDecision awardDecision, Tender tender);

    AwardDecision findById(Integer id);
    
}