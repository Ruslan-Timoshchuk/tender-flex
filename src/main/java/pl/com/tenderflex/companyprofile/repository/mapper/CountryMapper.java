package pl.com.tenderflex.companyprofile.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.companyprofile.model.Country;

@Component
public class CountryMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCountry(resultSet);
    }
    
    public Country mapCountry(ResultSet resultSet) throws SQLException {
        return Country
                .builder()
                .id(resultSet.getInt("country_id"))
                .name(resultSet.getString("country_name"))
                .build();
    }
    
}