package pl.com.tenderflex.service;

import pl.com.tenderflex.model.AwardDecision;

public interface AwardDecisionService {

    AwardDecision save(AwardDecision awardDecision);

    AwardDecision findById(Integer id);
    
}