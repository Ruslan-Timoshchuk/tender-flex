package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency>{

    @Override
    public Currency mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getInt("id"));
        currency.setCurrencyType(resultSet.getString("currency_type"));
        return currency;
    }
}