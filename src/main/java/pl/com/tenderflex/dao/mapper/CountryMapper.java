package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;

@Component
public class CountryMapper implements RowMapper<Country> {

    @Override
    public Country mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Country country = new Country();
        country.setId(resultSet.getInt("id"));
        country.setCountryName(resultSet.getString("country_name"));
        return country;
    }
}