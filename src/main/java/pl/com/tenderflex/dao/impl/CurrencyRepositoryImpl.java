package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CurrencyRepository;
import pl.com.tenderflex.dao.mapper.CurrencyMapper;
import pl.com.tenderflex.model.Currency;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    public static final String GET_ALL_CURRENCIES_QUERY = "SELECT id, currency_type FROM currencies";

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> getAll() {
        return jdbcTemplate.query(GET_ALL_CURRENCIES_QUERY, currencyMapper);
    }
}