package pl.com.tenderflex.repository.impl;

import static java.lang.String.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.repository.CountryRepository;
import pl.com.tenderflex.repository.mapper.CountryMapper;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryRepositoryImpl.class);
    
    public static final String FIND_ALL_PATTERN_QUERY = "SELECT %s, %s FROM %s";
    public static final String COUNTRY_ID = "id";
    public static final String COUNTRY_NAME = "name";
    public static final String COUNTRIES_TABLE = "countries";
    
    private final JdbcTemplate jdbcTemplate;
    private final CountryMapper countryMapper;
    
    @Override
    public List<Country> findAll() {
        String sqlQuery = format(FIND_ALL_PATTERN_QUERY, COUNTRY_ID, COUNTRY_NAME, COUNTRIES_TABLE);
        LOGGER.debug("Executing SQL Query: {}", sqlQuery);
        List<Country> countries = jdbcTemplate.query(sqlQuery, countryMapper);
        LOGGER.info("Successfully fetched {} countries", countries.size());
        return countries;
    }

}