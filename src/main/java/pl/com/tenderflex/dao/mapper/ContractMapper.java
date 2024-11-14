package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;

@Component
@RequiredArgsConstructor
public class ContractMapper implements RowMapper<Contract>{

    private final ContractTypeMapper contractTypeMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMapper fileMapper;
    
    @Override
    public Contract mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Contract
                .builder()
                .id(resultSet.getInt("id"))
                .contractType(contractTypeMapper.mapRow(resultSet, rowNum))
                .minPrice(resultSet.getInt("min_price"))
                .maxPrice(resultSet.getInt("max_price"))
                .currency(currencyMapper.mapRow(resultSet, rowNum))
                .contractFile(fileMapper.mapRow(resultSet, rowNum))
                .signedDeadline(resultSet.getObject("signed_deadline", LocalDate.class))
                .signedDate(resultSet.getObject("signed_date", LocalDate.class))
                .build();
    }  
    
}