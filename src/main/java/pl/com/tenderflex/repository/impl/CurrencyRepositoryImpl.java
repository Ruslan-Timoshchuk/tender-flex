package pl.com.tenderflex.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.repository.CurrencyRepository;
import pl.com.tenderflex.repository.mapper.CurrencyMapper;

@Repository
@RequiredArgsConstructor
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRepositoryImpl.class);

    public static final String FIND_ALL_CURRENCIES_QUERY = "SELECT id, code, symbol FROM currencies";

    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        List<Currency> currencies = jdbcTemplate.query(FIND_ALL_CURRENCIES_QUERY, currencyMapper);
        LOGGER.info("Successfully fetched {} currencies", currencies.size());
        return currencies;
    }

}