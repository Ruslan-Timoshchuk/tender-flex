package com.flex.tender.service;

import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.Tender;

public interface RejectDecisionService {

    RejectDecision save(RejectDecision rejectDecision, Tender tender);

    RejectDecision findById(Integer id);

}