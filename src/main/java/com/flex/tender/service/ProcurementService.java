package com.flex.tender.service;

import com.flex.tender.payload.request.AwardOfferRequest;
import com.flex.tender.payload.request.InitiateProcurementRequest;
import com.flex.tender.payload.request.OfferRejectionRequest;
import com.flex.tender.payload.request.OfferSubmissionRequest;
import com.flex.tender.payload.request.ProcurementCompletionRequest;
import com.flex.tender.payload.request.ProcurementRejectionRequest;
import com.flex.tender.payload.response.AwardResultResponse;
import com.flex.tender.payload.response.OfferRejectionResponse;
import com.flex.tender.payload.response.OfferSubmissionResponse;
import com.flex.tender.payload.response.ProcurementCompletionResponse;
import com.flex.tender.payload.response.ProcurementInitiationResponse;
import com.flex.tender.payload.response.ProcurementRejectionResponse;

public interface ProcurementService {

    ProcurementInitiationResponse initiateProcurement(InitiateProcurementRequest initiateProcurementRequest);
    
    OfferSubmissionResponse sendNewOffer(OfferSubmissionRequest offerSubmissionRequest);

    AwardResultResponse makeAnAwardDecision(AwardOfferRequest awardOfferRequest);

    ProcurementCompletionResponse completeProcurement(ProcurementCompletionRequest procurementCompletionRequest);

    OfferRejectionResponse rejectUnsuitableOffer(OfferRejectionRequest offerRejectionRequest);

    ProcurementRejectionResponse rejectProcurement(ProcurementRejectionRequest procurementRejectionRequest);

}