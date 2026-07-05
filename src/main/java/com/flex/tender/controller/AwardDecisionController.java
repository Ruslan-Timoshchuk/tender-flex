package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.request.AwardOfferDecisionRequest;
import com.flex.tender.payload.request.DeclineContractDecisionRequest;
import com.flex.tender.service.facade.AwardDecisionManager;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.award.decisions.path}")
@RequiredArgsConstructor
public class AwardDecisionController {

    public static final String URL_AWARD_OFFER = "/award-offer";
    public static final String URL_DECLINE_CONTRACT = "/decline-contract";
    
    private final AwardDecisionManager awardDecisionManager;
    
    @Secured(CONTRACTOR)
    @PatchMapping(path = URL_AWARD_OFFER, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> applyAwardDecision(
            @RequestBody AwardOfferDecisionRequest awardOfferDecisionRequest) {
        awardDecisionManager.applyAwardDecision(awardOfferDecisionRequest);
        return ResponseEntity
                 .noContent()
                 .build();
    }
    
    @Secured(BIDDER)
    @PatchMapping(path = URL_DECLINE_CONTRACT, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> declineContract(
            @RequestBody DeclineContractDecisionRequest declineContractDecisionRequest) {
        awardDecisionManager.declineContract(declineContractDecisionRequest);
        return ResponseEntity
                 .noContent()
                 .build();
    }

}