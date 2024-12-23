package pl.com.tenderflex.contract.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.contract.model.ContractType;

@Component
public class ContractTypeMapper implements RowMapper<ContractType>{

    @Override
    public ContractType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapContractType(resultSet);
    }

    public ContractType mapContractType(ResultSet resultSet) throws SQLException {
        return ContractType
                .builder()
                .id(resultSet.getInt("contract_type_id"))
                .title(resultSet.getString("title"))
                .build();
    }
    
}