package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency> {

    public static final String CURRENCY_ID = "id";
    public static final String CURRENCY_CODE = "code";
    public static final String CURRENCY_SYMBOL = "symbol";
    
    @Override
    public Currency mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCurrency(resultSet, Map.of(
                CURRENCY_ID, CURRENCY_ID,
                CURRENCY_CODE, CURRENCY_CODE,
                CURRENCY_SYMBOL, CURRENCY_SYMBOL));
    }
    
    public Currency mapCurrency(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return Currency
                .builder()
                .id(resultSet.getInt(columnLabels.get(CURRENCY_ID)))
                .code(resultSet.getString(columnLabels.get(CURRENCY_CODE)))
                .symbol(resultSet.getString(columnLabels.get(CURRENCY_SYMBOL)))
                .build();
    }
    
}