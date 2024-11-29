package pl.com.tenderflex.currency.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.currency.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency> {

    @Override
    public Currency mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Currency
                .builder()
                .id(resultSet.getInt("id"))
                .currencyType(resultSet.getString("currency_type"))
                .build();
    }
    
}