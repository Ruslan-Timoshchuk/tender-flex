package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.flex.tender.payload.response.ContractResponse;
import com.flex.tender.service.ContractService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ContractController {

    public static final String URL_CONTRACTS_ID = "/api/v1/contracts/{id}";
   
    private final ContractService contractService;
    
    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping(URL_CONTRACTS_ID)
    public ContractResponse findById(@PathVariable("id") Integer id) {
        return contractService.findDetailsById(id);
    }
    
}