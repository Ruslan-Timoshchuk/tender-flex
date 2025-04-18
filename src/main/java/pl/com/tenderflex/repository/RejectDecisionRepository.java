package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.RejectDecision;

public interface RejectDecisionRepository {

    RejectDecision save(RejectDecision reject);

    RejectDecision findById(Integer id);
    
}