package pl.com.tenderflex.companyprofile.service;

import java.util.List;

import pl.com.tenderflex.companyprofile.payload.CountryResponse;

public interface CountryService {

    List<CountryResponse> getAllCountries();
    
}