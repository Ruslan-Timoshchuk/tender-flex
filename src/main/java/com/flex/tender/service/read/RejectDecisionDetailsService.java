package com.flex.tender.service.details;

import com.flex.tender.model.RejectDecision;

public interface RejectDecisionDetailsService {

    RejectDecision findById(Integer id);

    RejectDecision findByTenderId(Integer id);

}
