package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.ContractTypeColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.ContractType;

@Component
public class ContractTypeMapper implements RowMapper<ContractType> {

    @Override
    public ContractType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapContractType(resultSet);
    }

    public ContractType mapContractType(ResultSet resultSet) throws SQLException {
        return ContractType.builder()
                           .id(resultSet.getInt(CONTRACT_TYPE_ID))
                           .title(resultSet.getString(CONTRACT_TYPE_TITLE))
               .build();

    }

}