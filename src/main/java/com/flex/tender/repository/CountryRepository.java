package com.flex.tender.repository;

import java.util.List;

import com.flex.tender.model.Country;

public interface CountryRepository {
    
    List<Country> findAll();
    
}