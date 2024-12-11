package pl.com.tenderflex.companyprofile.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.model.Country;
import pl.com.tenderflex.companyprofile.repository.CountryRepository;
import pl.com.tenderflex.companyprofile.repository.mapper.CountryMapper;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepository {

    public static final String GET_ALL_COUNTRIES_QUERY = "SELECT id AS country_id, country_name FROM countries";
    
    private final JdbcTemplate jdbcTemplate;
    private final CountryMapper countryMapper;
    
    @Override
    public List<Country> getAll(){
        return jdbcTemplate.query(GET_ALL_COUNTRIES_QUERY, countryMapper);
    }
    
}