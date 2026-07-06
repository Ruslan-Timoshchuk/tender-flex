package com.flex.tender.controller;

import static com.flex.tender.controller.constant.SecuredAuthorities.*;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.flex.tender.payload.response.CurrencyResponse;
import com.flex.tender.service.read.CurrencyDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.base.path}/${api.v1}/${api.currencies.path}")
public class CurrencyController {

    private final CurrencyDetailsService currencyDetailsService;

    @Secured({ CONTRACTOR, BIDDER })
    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> findAll() {
        return ResponseEntity
                   .ok(currencyDetailsService.findAll());
    }

}