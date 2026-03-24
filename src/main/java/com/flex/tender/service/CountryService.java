package com.flex.tender.service;

import java.util.List;

import com.flex.tender.payload.response.CountryResponse;

public interface CountryService {

    List<CountryResponse> getAllCountries();
    
}