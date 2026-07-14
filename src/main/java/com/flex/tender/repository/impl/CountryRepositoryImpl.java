package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.mixins.CountryMixins.*;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Country;
import com.flex.tender.repository.CountryRepository;
import com.flex.tender.repository.mapper.CountryMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CountryMapper countryMapper;

    @Override
    public List<Country> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, countryMapper);
    }

    @Override
    public Country findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, countryMapper, id);
    }

}