package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContractType;

@Component
public class ContractTypeMapper implements RowMapper<ContractType> {

    public static final String TYPE_ID = "contract_type_id";
    public static final String TYPE_TITLE = "contract_type_name";

    @Override
    public ContractType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapContractType(resultSet);
    }

    public ContractType mapContractType(ResultSet resultSet) throws SQLException {
        return ContractType
                .builder()
                .id(resultSet.getInt(TYPE_ID))
                .title(resultSet.getString(TYPE_TITLE))
                .build();

    }

}