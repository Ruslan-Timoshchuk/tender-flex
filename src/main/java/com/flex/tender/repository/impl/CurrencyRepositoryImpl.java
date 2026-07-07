package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.CurrencyQueries.*;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Currency;
import com.flex.tender.repository.CurrencyRepository;
import com.flex.tender.repository.mapper.CurrencyMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, currencyMapper);
    }

    @Override
    public Currency findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, currencyMapper, id);
    }

}