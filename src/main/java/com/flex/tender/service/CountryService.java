package com.flex.tender.service;

import java.util.List;
import com.flex.tender.model.Country;
import com.flex.tender.payload.response.CountryResponse;

public interface CountryService {

    List<CountryResponse> findAll();

    Country findById(Integer id);
    
}