package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.TypeOfTender;

@Component
public class TypeOfTenderMapper implements RowMapper<TypeOfTender>{

    @Override
    public TypeOfTender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return TypeOfTender
                .builder()
                .id(resultSet.getInt("id"))
                .title(resultSet.getString("title"))
                .build();
    }  
}