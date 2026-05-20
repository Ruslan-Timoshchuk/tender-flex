package com.flex.tender.service;

import com.flex.tender.model.RejectDecision;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;

public interface RejectDecisionService {

    RejectDecisionResponse save(RejectDecisionRequest rejectDecisionRequest);
    
    RejectDecision findById(Integer id);

}