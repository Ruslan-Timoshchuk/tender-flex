package com.flex.tender.payload.mapstract;

import org.mapstruct.Mapper;

import com.flex.tender.model.Country;
import com.flex.tender.payload.request.CountryRequest;
import com.flex.tender.payload.response.CountryResponse;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toEntity(CountryRequest countryRequest);
    
    CountryResponse toResponse(Country country);
    
}