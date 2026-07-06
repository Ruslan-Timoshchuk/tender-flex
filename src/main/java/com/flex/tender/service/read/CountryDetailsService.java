package com.flex.tender.service.read;

import java.util.List;
import com.flex.tender.model.Country;
import com.flex.tender.payload.response.CountryResponse;

public interface CountryDetailsService {

    List<CountryResponse> findAll();

    Country findById(Integer id);
    
}