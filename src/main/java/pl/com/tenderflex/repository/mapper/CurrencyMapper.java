package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.impl.CurrencyRepositoryImpl.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency> {

    @Override
    public Currency mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCurrency(resultSet, Map.of(
                CURRENCY_ID, CURRENCY_ID,
                CURRENCY_TYPE, CURRENCY_TYPE));
    }
    
    public Currency mapCurrency(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return Currency
                .builder()
                .id(resultSet.getInt(columnLabels.get(CURRENCY_ID)))
                .type(resultSet.getString(columnLabels.get(CURRENCY_TYPE)))
                .build();
    }
    
}