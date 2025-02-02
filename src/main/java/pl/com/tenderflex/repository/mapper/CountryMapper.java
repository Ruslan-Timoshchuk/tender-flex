package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
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
        return mapCountry(resultSet, 
                Map.of(COUNTRY_ID, COUNTRY_ID, 
                       COUNTRY_NAME, COUNTRY_NAME,
                       COUNTRY_ISO_CODE, COUNTRY_ISO_CODE,
                       COUNTRY_PHONE_CODE, COUNTRY_PHONE_CODE));
    }
    
    public Country mapCountry(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return Country
                .builder()
                .id(resultSet.getInt(columnLabels.get(COUNTRY_ID)))
                .name(resultSet.getString(columnLabels.get(COUNTRY_NAME)))
                .isoCode(resultSet.getString(columnLabels.get(COUNTRY_ISO_CODE)))
                .phoneCode(resultSet.getString(columnLabels.get(COUNTRY_PHONE_CODE)))
                .build();
    }
    
}