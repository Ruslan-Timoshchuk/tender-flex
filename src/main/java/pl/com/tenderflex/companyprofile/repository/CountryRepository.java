package pl.com.tenderflex.companyprofile.repository;

import java.util.List;

import pl.com.tenderflex.companyprofile.model.Country;

public interface CountryRepository {

    List<Country> getAll();
    
}