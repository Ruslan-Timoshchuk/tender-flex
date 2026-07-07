package com.flex.tender.repository;

import com.flex.tender.model.RejectDecision;

public interface RejectDecisionRepository {

    RejectDecision save(RejectDecision reject);

    RejectDecision findById(Integer id);

    RejectDecision findByTenderId(Integer id);
    
}