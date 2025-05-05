package pl.com.tenderflex.service;

import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Tender;

public interface AwardDecisionService {

    AwardDecision save(AwardDecision awardDecision, Tender tender);

    AwardDecision findById(Integer id);
    
}