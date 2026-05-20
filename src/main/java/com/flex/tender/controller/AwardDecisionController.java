package com.flex.tender.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.request.AwardDecisionRequest;
import com.flex.tender.payload.response.AwardDecisionResponse;
import com.flex.tender.service.AwardDecisionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.award.decisions.path}")
@RequiredArgsConstructor
public class AwardDecisionController {

    private final AwardDecisionService awardDecisionService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<AwardDecisionResponse> save(@RequestBody AwardDecisionRequest awardDecisionRequest) {
        return ResponseEntity
                   .ok(awardDecisionService.save(awardDecisionRequest));
    }

}