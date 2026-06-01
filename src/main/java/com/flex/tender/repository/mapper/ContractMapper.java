package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.ContractColumns.*;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContractMapper implements RowMapper<Contract> {
    
    private final ContractTypeMapper contractTypeMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
     
    @Override
    public Contract mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Contract
                .builder()
                .id(resultSet.getInt(CONTRACT_ID))
                .contractType(contractTypeMapper.mapContractType(resultSet))
                .minPrice(resultSet.getInt(MIN_PRICE))
                .maxPrice(resultSet.getInt(MAX_PRICE))
                .currency(currencyMapper.mapCurrency(resultSet))
                .fileMetadata(fileMeatadataMapper.
                        mapFileMetadata(resultSet, 
                                Map.of(FILE_ID, CONTRACT_FILE_ID,
                                       FILE_NAME, CONTRACT_FILE_NAME,
                                       FILE_CONTENT_TYPE, CONTRACT_FILE_CONTENT_TYPE,
                                       FILE_AWS3_KEY, CONTRACT_FILE_AWS3_KEY)))
                .globalStatus(EContractStatus.valueOf(resultSet.getString(GLOBAL_STATUS)))
                .signedDeadline(resultSet.getObject(SIGNED_DEADLINE, LocalDate.class))
                .signedDate(resultSet.getObject(SIGNED_DATE, LocalDate.class))
                .build();
    }
    
}