package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Country;
import com.flex.tender.repository.CountryRepository;
import com.flex.tender.repository.mapper.CountryMapper;
import com.flex.tender.repository.sql.mixins.CountryMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    public static final String FIND_ALL_QUERY = """
            SELECT %s 
            FROM countries country
            """.formatted(
                CountryMixins.COUNTRY_QUERY_COLUMNS);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s 
            FROM countries country 
            WHERE id = :id
            """.formatted(
                CountryMixins.COUNTRY_QUERY_COLUMNS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final CountryMapper countryMapper;

    @Override
    public List<Country> findAll() {
        return jdbc.query(FIND_ALL_QUERY, countryMapper);
    }

    @Override
    public Country findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, countryMapper);
    }

}