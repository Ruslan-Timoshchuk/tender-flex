package com.flex.tender.controller;

import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flex.tender.payload.response.CountryResponse;
import com.flex.tender.service.CountryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {

    public static final String URL_COUNTRIES_ALL = "/all";
    
    private final CountryService countryService;
    
    @Secured({ "CONTRACTOR", "BIDDER" })
    @GetMapping(URL_COUNTRIES_ALL)
    public List<CountryResponse> getAllCountries() {
        return countryService.getAllCountries();
    }
    
}