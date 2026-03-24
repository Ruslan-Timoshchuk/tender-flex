package com.flex.tender.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flex.tender.model.Currency;
import com.flex.tender.repository.CurrencyRepository;
import com.flex.tender.repository.mapper.CurrencyMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRepositoryImpl.class);

    public static final String FIND_ALL_CURRENCIES_QUERY = "SELECT id AS currency_id, code, symbol FROM currencies";

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = jdbcTemplate.query(FIND_ALL_CURRENCIES_QUERY, currencyMapper);
        LOGGER.info("Successfully fetched {} currencies", currencies.size());
        return currencies;
    }

}