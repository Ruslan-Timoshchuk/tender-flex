package pl.com.tenderflex.dao;

import pl.com.tenderflex.model.RejectDecision;

public interface RejectRepository {

    RejectDecision save(RejectDecision reject);
    
}
