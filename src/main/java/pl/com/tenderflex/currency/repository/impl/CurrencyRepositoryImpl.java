package pl.com.tenderflex.currency.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.currency.model.Currency;
import pl.com.tenderflex.currency.repository.CurrencyRepository;
import pl.com.tenderflex.currency.repository.mapper.CurrencyMapper;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    public static final String FIND_ALL_CURRENCIES_QUERY = "SELECT id, currency_type FROM currencies";

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        return jdbcTemplate.query(FIND_ALL_CURRENCIES_QUERY, currencyMapper);
    }
}