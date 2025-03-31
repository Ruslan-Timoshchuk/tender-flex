package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency> {

    public static final String CURRENCY_ID = "currency_id";
    public static final String CURRENCY_CODE = "code";
    public static final String CURRENCY_SYMBOL = "symbol";
    
    @Override
    public Currency mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCurrency(resultSet);
    }
    
    public Currency mapCurrency(ResultSet resultSet) throws SQLException {
        return Currency
                .builder()
                .id(resultSet.getInt(CURRENCY_ID))
                .code(resultSet.getString(CURRENCY_CODE))
                .symbol(resultSet.getString(CURRENCY_SYMBOL))
                .build();
    }
    
}