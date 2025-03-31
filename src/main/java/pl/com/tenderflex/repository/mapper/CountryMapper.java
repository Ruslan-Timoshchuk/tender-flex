package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;

@Component
public class CountryMapper implements RowMapper<Country> { 
   
    public static final String COUNTRY_ID = "country_id";
    public static final String COUNTRY_NAME = "name";
    public static final String COUNTRY_ISO_CODE = "iso_code";
    public static final String COUNTRY_PHONE_CODE = "phone_code";
    
    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCountry(resultSet);
    }
    
    public Country mapCountry(ResultSet resultSet) throws SQLException {
        return Country
                .builder()
                .id(resultSet.getInt(COUNTRY_ID))
                .name(resultSet.getString(COUNTRY_NAME))
                .isoCode(resultSet.getString(COUNTRY_ISO_CODE))
                .phoneCode(resultSet.getString(COUNTRY_PHONE_CODE))
                .build();
    }
    
}