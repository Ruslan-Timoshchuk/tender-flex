package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.response.ContractTypeResponse;
import com.flex.tender.service.read.ContractTypeDetailsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.contract.type.path}")
public class ContractTypeController {

    private final ContractTypeDetailsService contractTypeDetailsService;

    @Secured(CONTRACTOR)
    @GetMapping
    public ResponseEntity<List<ContractTypeResponse>> findAll() {
        return ResponseEntity
                   .ok(contractTypeDetailsService.findAll());
    }

}