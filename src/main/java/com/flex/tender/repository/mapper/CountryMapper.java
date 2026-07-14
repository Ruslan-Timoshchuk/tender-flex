package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.CountryColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Country;

@Component
public class CountryMapper implements RowMapper<Country> { 

    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCountry(resultSet);
    }
    
    public Country mapCountry(ResultSet resultSet) throws SQLException {
        return Country.builder()
                      .id(resultSet.getInt(COUNTRY_ID))
                      .name(resultSet.getString(COUNTRY_NAME))
                      .isoCode(resultSet.getString(COUNTRY_ISO_CODE))
                      .phoneCode(resultSet.getString(COUNTRY_PHONE_CODE))
               .build();
    }
    
}