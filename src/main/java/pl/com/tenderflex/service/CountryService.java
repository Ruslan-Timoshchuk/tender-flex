package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.payload.iresponse.response.CountryResponse;

public interface CountryService {

    List<CountryResponse> getAllCountries();
    
}