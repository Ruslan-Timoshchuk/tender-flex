package pl.com.tenderflex.contract.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.contract.model.Contract;
import pl.com.tenderflex.currency.repository.mapper.CurrencyMapper;
import pl.com.tenderflex.dao.mapper.FileMeatadataMapper;

@Component
@RequiredArgsConstructor
public class ContractMapper implements RowMapper<Contract>{
    
    private final ContractTypeMapper contractTypeMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
    
    @Override
    public Contract mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Contract
                .builder()
                .id(resultSet.getInt("id"))
                .contractType(contractTypeMapper.mapContractType(resultSet))
                .minPrice(resultSet.getInt("min_price"))
                .maxPrice(resultSet.getInt("max_price"))
                .currency(currencyMapper.mapCurrency(resultSet))
                .fileMetadata(fileMeatadataMapper.mapFileMetadata(resultSet))
                .signedDeadline(resultSet.getObject("signed_deadline", LocalDate.class))
                .signedDate(resultSet.getObject("signed_date", LocalDate.class))
                .build();
    }  
    
}