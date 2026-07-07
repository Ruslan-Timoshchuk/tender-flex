package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.CurrencyColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Currency;

@Component
public class CurrencyMapper implements RowMapper<Currency> {
    
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