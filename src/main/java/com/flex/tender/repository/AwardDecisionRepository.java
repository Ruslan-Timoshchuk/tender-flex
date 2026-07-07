package com.flex.tender.repository;

import com.flex.tender.model.AwardDecision;

public interface AwardDecisionRepository {

    AwardDecision save(AwardDecision award);
    
    AwardDecision findById(Integer id);

    AwardDecision findByTenderId(Integer id);
    
}