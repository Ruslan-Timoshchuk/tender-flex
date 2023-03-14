package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CountryRepository;
import pl.com.tenderflex.dao.mapper.CountryMapper;
import pl.com.tenderflex.model.Country;

@Repository
@RequiredArgsConstructor
public class CountryDao implements CountryRepository {

    public static final String GET_ALL_COUNTRIES_QUERY = "SELECT * FROM countries";
    public static final String GET_COUNTRY_BY_ID_QUERY = "SELECT * FROM countries WHERE id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final CountryMapper countryMapper;
    
    @Override
    public List<Country> getAll(){
        return jdbcTemplate.query(GET_ALL_COUNTRIES_QUERY, countryMapper);
    }
    
    @Override
    public Country getById(Integer countryId) {
        return jdbcTemplate.queryForObject(GET_COUNTRY_BY_ID_QUERY, countryMapper, countryId);
    }
}