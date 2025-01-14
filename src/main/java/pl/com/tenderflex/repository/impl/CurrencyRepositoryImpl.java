package pl.com.tenderflex.repository.impl;

import static java.lang.String.format;
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

    public static final String FIND_ALL_PATTERN_QUERY = "SELECT %s, %s FROM %s";
    public static final String CURRENCY_ID = "id";
    public static final String CURRENCY_TYPE = "type";
    public static final String CURRENCIES_TABLE = "currencies";
    
    private final JdbcTemplate jdbcTemplate;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> findAll() {
        String sqlQuery = format(FIND_ALL_PATTERN_QUERY, CURRENCY_ID, CURRENCY_TYPE, CURRENCIES_TABLE);
        LOGGER.debug("Executing SQL Query: {}", sqlQuery);
        List<Currency> currencies = jdbcTemplate.query(sqlQuery, currencyMapper);
        LOGGER.info("Successfully fetched {} currencies", currencies.size());
        return currencies;
    }
    
}