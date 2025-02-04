package pl.com.tenderflex.repository;

import pl.com.tenderflex.model.AwardDecision;

public interface AwardDecisionRepository {

    AwardDecision save(AwardDecision award);
    
}