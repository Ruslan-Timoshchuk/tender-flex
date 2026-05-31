package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.request.RejectDecisionRequest;
import com.flex.tender.payload.response.RejectDecisionResponse;
import com.flex.tender.service.transactional.RejectDecisionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.reject.decisions.path}")
@RequiredArgsConstructor
public class RejectDecisionController {

    private final RejectDecisionService rejectDecisionService;

    @Secured( CONTRACTOR )
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<RejectDecisionResponse> save(@RequestBody RejectDecisionRequest rejectDecisionRequest) {
        return ResponseEntity
                   .ok(rejectDecisionService.save(rejectDecisionRequest));
    }

}