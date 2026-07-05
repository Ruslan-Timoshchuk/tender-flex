package com.flex.tender.service.facade;

import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.payload.request.DeclineContractDecisionRequest;

public interface AwardDecisionManager {

    void applyAwardDecision(AwardOfferDecisionRequest awardOfferDecisionRequest);

    void declineContract(DeclineContractDecisionRequest declineContractDecisionRequest);

}
