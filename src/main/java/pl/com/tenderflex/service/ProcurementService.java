package pl.com.tenderflex.service;

import pl.com.tenderflex.payload.request.AwardOfferRequest;
import pl.com.tenderflex.payload.request.OfferRejectionRequest;
import pl.com.tenderflex.payload.request.OfferSubmissionRequest;
import pl.com.tenderflex.payload.request.ProcurementRequest;
import pl.com.tenderflex.payload.request.SigningContractRequest;
import pl.com.tenderflex.payload.response.AwardResultResponse;
import pl.com.tenderflex.payload.response.OfferRejectionResponse;
import pl.com.tenderflex.payload.response.OfferSubmissionResponse;
import pl.com.tenderflex.payload.response.ProcurementResponse;
import pl.com.tenderflex.payload.response.SigningContractResponse;

public interface ProcurementService {

    ProcurementResponse initiateProcurement(ProcurementRequest procurementRequest);
    
    OfferSubmissionResponse sendNewOffer(OfferSubmissionRequest offerSubmissionRequest);

    AwardResultResponse makeAnAwardDecision(AwardOfferRequest awardOfferRequest);

    SigningContractResponse approveContract(SigningContractRequest signingContractRequest);

    OfferRejectionResponse rejectUnsuitableOffer(OfferRejectionRequest offerRejectionRequest);

}