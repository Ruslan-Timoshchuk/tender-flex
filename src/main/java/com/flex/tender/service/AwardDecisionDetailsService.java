package com.flex.tender.service;

import com.flex.tender.model.AwardDecision;

public interface AwardDecisionDetailsService {

    AwardDecision findById(Integer id);

    AwardDecision findByTenderId(Integer id);
    
}