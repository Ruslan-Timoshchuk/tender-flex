package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Currency;
import com.flex.tender.repository.CurrencyRepository;
import com.flex.tender.repository.mapper.CurrencyMapper;
import com.flex.tender.repository.sql.mixins.CurrencyMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    public static final String FIND_ALL_QUERY = """
           SELECT %s  
           FROM currencies currency
           """.formatted(
               CurrencyMixins.CURRENCY_QUERY_COLUMNS);
    public static final String FIND_BY_ID_QUERY = """
           SELECT %s 
           FROM currencies currency 
           WHERE id = :id
           """.formatted(
               CurrencyMixins.CURRENCY_QUERY_COLUMNS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        return jdbc.query(FIND_ALL_QUERY, currencyMapper);
    }

    @Override
    public Currency findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, currencyMapper);
    }

}