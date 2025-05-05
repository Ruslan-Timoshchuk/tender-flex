package pl.com.tenderflex.service;

import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;

public interface RejectDecisionService {

    RejectDecision save(RejectDecision rejectDecision, Tender tender);

    RejectDecision findById(Integer id);

}