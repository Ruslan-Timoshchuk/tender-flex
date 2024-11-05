package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContractType;

@Component
public class ContractTypeMapper implements RowMapper<ContractType>{

    @Override
    public ContractType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return ContractType
                .builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .build();
    }  
}