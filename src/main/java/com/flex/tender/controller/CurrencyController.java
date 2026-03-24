package com.flex.tender.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex.tender.payload.response.CurrencyResponse;
import com.flex.tender.service.CurrencyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/currencies")
public class CurrencyController {

    public static final String URL_CURRENCIES_ALL = "/all";
        
    private final CurrencyService currencyService;

    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping(URL_CURRENCIES_ALL)
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }
    
}