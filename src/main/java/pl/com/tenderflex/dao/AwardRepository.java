package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.AwardDecision;

public interface AwardRepository {

    AwardDecision save(AwardDecision award);
    
}