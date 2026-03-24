package com.flex.tender.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flex.tender.model.Country;
import com.flex.tender.repository.CountryRepository;
import com.flex.tender.repository.mapper.CountryMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryRepositoryImpl.class);

    public static final String FIND_ALL_QUERY = "SELECT id AS country_id, name, iso_code, phone_code FROM countries";

    private final JdbcTemplate jdbcTemplate;
    private final CountryMapper countryMapper;

    @Override
    public List<Country> findAll() {
        List<Country> countries = jdbcTemplate.query(FIND_ALL_QUERY, countryMapper);
        LOGGER.info("Successfully fetched {} countries", countries.size());
        return countries;
    }

}