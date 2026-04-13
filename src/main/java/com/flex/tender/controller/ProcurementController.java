package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
import com.flex.tender.service.ProcurementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProcurementController {

    public static final String URI_PROCUREMENTS = "/api/v1/procurements";
    public static final String URI_PROCUREMENTS_SEND_OFFER = "/api/v1/procurements/send-offer";
    public static final String URI_PROCUREMENTS_AWARD_OFFER = "/api/v1/procurements/award-offer";
    public static final String URI_PROCUREMENTS_CONTRACT_SIGN = "/api/v1/procurements/contract-sign";
    public static final String URI_PROCUREMENTS_OFFER_REJECT = "/api/v1/procurements/offer-reject";
    public static final String URI_PROCUREMENTS_CONTRACT_REJECT = "/api/v1/procurements/contract-reject";

    private final ProcurementService procurementService;

    @Secured(CONTRACTOR)
    @PostMapping(value = URI_PROCUREMENTS, consumes = APPLICATION_JSON_VALUE)
    public ProcurementInitiationResponse initiateProcurement(
            @RequestBody InitiateProcurementRequest procurementRequest) {
        return procurementService.initiateProcurement(procurementRequest);
    }

    @Secured(BIDDER)
    @PostMapping(value = URI_PROCUREMENTS_SEND_OFFER, consumes = APPLICATION_JSON_VALUE)
    public OfferSubmissionResponse sendNewOffer(@RequestBody OfferSubmissionRequest offerSubmissionRequest) {
        return procurementService.sendNewOffer(offerSubmissionRequest);
    }

    @Secured(CONTRACTOR)
    @PatchMapping(value = URI_PROCUREMENTS_AWARD_OFFER, consumes = APPLICATION_JSON_VALUE)
    public AwardResultResponse makeAnAwardDecision(@RequestBody AwardOfferRequest awardOfferRequest) {
        return procurementService.makeAnAwardDecision(awardOfferRequest);
    }

    @Secured(BIDDER)
    @PatchMapping(value = URI_PROCUREMENTS_CONTRACT_SIGN, consumes = APPLICATION_JSON_VALUE)
    public ProcurementCompletionResponse completeProcurement(
            @RequestBody ProcurementCompletionRequest procurementCompletionRequest) {
        return procurementService.completeProcurement(procurementCompletionRequest);
    }

    @Secured(CONTRACTOR)
    @PatchMapping(value = URI_PROCUREMENTS_OFFER_REJECT, consumes = APPLICATION_JSON_VALUE)
    public OfferRejectionResponse rejectOffer(@RequestBody OfferRejectionRequest offerRejectionRequest) {
        return procurementService.rejectUnsuitableOffer(offerRejectionRequest);
    }

    @Secured(BIDDER)
    @PatchMapping(value = URI_PROCUREMENTS_CONTRACT_REJECT, consumes = APPLICATION_JSON_VALUE)
    public ProcurementRejectionResponse rejectProcurement(
            @RequestBody ProcurementRejectionRequest procurementRejectionRequest) {
        return procurementService.rejectProcurement(procurementRejectionRequest);
    }

}