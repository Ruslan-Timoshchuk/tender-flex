package pl.com.tenderflex.service;

import java.util.List;

import pl.com.tenderflex.payload.response.CountryResponse;

public interface CountryService {

    List<CountryResponse> getAllCountries();
    
}