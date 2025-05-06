package pl.com.tenderflex.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static pl.com.tenderflex.security.SecurityRoles.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.payload.request.AwardOfferRequest;
import pl.com.tenderflex.payload.request.OfferRejectionRequest;
import pl.com.tenderflex.payload.request.OfferSubmissionRequest;
import pl.com.tenderflex.payload.request.InitiateProcurementRequest;
import pl.com.tenderflex.payload.request.CompleteProcurementRequest;
import pl.com.tenderflex.payload.response.AwardResultResponse;
import pl.com.tenderflex.payload.response.OfferRejectionResponse;
import pl.com.tenderflex.payload.response.OfferSubmissionResponse;
import pl.com.tenderflex.payload.response.ProcurementInitiationResponse;
import pl.com.tenderflex.payload.response.ProcurementCompletionResponse;
import pl.com.tenderflex.service.ProcurementService;

@RestController
@RequiredArgsConstructor
public class ProcurementController {

    public static final String URI_PROCUREMENTS = "/api/v1/procurements";
    public static final String URI_PROCUREMENTS_SEND_OFFER = "/api/v1/procurements/send-offer";
    public static final String URI_PROCUREMENTS_AWARD_OFFER = "/api/v1/procurements/award-offer";
    public static final String URI_PROCUREMENTS_CONTRACT_SIGN = "/api/v1/procurements/contract-sign";
    public static final String URI_PROCUREMENTS_OFFER_REJECT = "/api/v1/procurements/offer-reject";

    private final ProcurementService procurementService;

    @Secured(CONTRACTOR)
    @PostMapping(value = URI_PROCUREMENTS, consumes = APPLICATION_JSON_VALUE)
    public ProcurementInitiationResponse initiateProcurement(@RequestBody InitiateProcurementRequest procurementRequest) {
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
    public ProcurementCompletionResponse completeProcurement(@RequestBody CompleteProcurementRequest completeProcurementRequest) {
        return procurementService.completeProcurement(completeProcurementRequest);
    }

    @Secured(CONTRACTOR)
    @PatchMapping(value = URI_PROCUREMENTS_OFFER_REJECT, consumes = APPLICATION_JSON_VALUE)
    public OfferRejectionResponse rejectOffer(@RequestBody OfferRejectionRequest offerRejectionRequest) {
        return procurementService.rejectUnsuitableOffer(offerRejectionRequest);
    }

}