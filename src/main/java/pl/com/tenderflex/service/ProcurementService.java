package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.request.AwardOfferRequest;
import pl.com.tenderflex.payload.request.OfferRejectionRequest;
import pl.com.tenderflex.payload.request.OfferSubmissionRequest;
import pl.com.tenderflex.payload.request.InitiateProcurementRequest;
import pl.com.tenderflex.payload.request.ProcurementCompletionRequest;
import pl.com.tenderflex.payload.request.ProcurementRejectionRequest;
import pl.com.tenderflex.payload.response.AwardResultResponse;
import pl.com.tenderflex.payload.response.OfferRejectionResponse;
import pl.com.tenderflex.payload.response.OfferSubmissionResponse;
import pl.com.tenderflex.payload.response.ProcurementInitiationResponse;
import pl.com.tenderflex.payload.response.ProcurementRejectionResponse;
import pl.com.tenderflex.payload.response.ProcurementCompletionResponse;

public interface ProcurementService {

    ProcurementInitiationResponse initiateProcurement(InitiateProcurementRequest initiateProcurementRequest);
    
    OfferSubmissionResponse sendNewOffer(OfferSubmissionRequest offerSubmissionRequest);

    AwardResultResponse makeAnAwardDecision(AwardOfferRequest awardOfferRequest);

    ProcurementCompletionResponse completeProcurement(ProcurementCompletionRequest procurementCompletionRequest);

    OfferRejectionResponse rejectUnsuitableOffer(OfferRejectionRequest offerRejectionRequest);

    ProcurementRejectionResponse rejectProcurement(ProcurementRejectionRequest procurementRejectionRequest);

}