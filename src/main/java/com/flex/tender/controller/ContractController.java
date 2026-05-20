package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.request.ContractRequest;
import com.flex.tender.payload.response.ContractResponse;
import com.flex.tender.service.ContractService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.base.path}/${api.v1}/${api.contracts.path}")
@RequiredArgsConstructor
public class ContractController {

    public static final String URL_CONTRACTS_ID = "/{id}";
   
    private final ContractService contractService;
    
    @Secured(CONTRACTOR)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<ContractResponse> save(@RequestBody ContractRequest contractRequest) {
        return ResponseEntity
                   .ok(contractService.save(contractRequest));
    }
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_CONTRACTS_ID)
    public ResponseEntity<ContractResponse> findById(@PathVariable("id") Integer id) {
        return ResponseEntity
                   .ok(contractService.findDetailsById(id));
    }
    
}