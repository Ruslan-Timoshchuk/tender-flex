package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.request.RejectOfferDecisionRequest;
import com.flex.tender.service.facade.RejectDecisionManager;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.reject.decisions.path}")
@RequiredArgsConstructor
public class RejectDecisionController {

    public static final String URL_REJECT_OFFER = "/reject-offer";
    
    private final RejectDecisionManager rejectDecisionManager;

    @Secured(CONTRACTOR)
    @PatchMapping(path = URL_REJECT_OFFER, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> applyRejectDecision(
            @RequestBody RejectOfferDecisionRequest rejectOfferDecisionRequest) {
        rejectDecisionManager.applyRejectDecision(rejectOfferDecisionRequest);
        return ResponseEntity
                 .noContent()
                 .build();
    }
    
}