package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.impl.CountryRepositoryImpl.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;

@Component
public class CountryMapper implements RowMapper<Country> { 
   
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCountry(resultSet, 
                Map.of(COUNTRY_ID, COUNTRY_ID, 
                       COUNTRY_NAME, COUNTRY_NAME));
    }
    
    public Country mapCountry(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return Country
                .builder()
                .id(resultSet.getInt(columnLabels.get(COUNTRY_ID)))
                .name(resultSet.getString(columnLabels.get(COUNTRY_NAME)))
                .build();
    }
    
}