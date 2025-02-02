package pl.com.tenderflex.repository;

import java.util.List;

import pl.com.tenderflex.model.Country;

public interface CountryRepository {
    
    List<Country> findAll();
    
}