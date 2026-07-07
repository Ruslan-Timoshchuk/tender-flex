package com.flex.tender.service.read;

import com.flex.tender.model.RejectDecision;

public interface RejectDecisionDetailsService {

    RejectDecision findById(Integer id);

    RejectDecision findByTenderId(Integer id);

}
