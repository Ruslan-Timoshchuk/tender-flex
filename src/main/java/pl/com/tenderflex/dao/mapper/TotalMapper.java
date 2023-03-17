package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Total;

@Component
public class TotalMapper implements RowMapper<Total> {

    @Override
    public Total mapRow(ResultSet resultSet, int rowNum) throws SQLException {
           return Total
                   .builder()
                   .tenders(resultSet.getInt("tenders"))
                   .offers(resultSet.getInt("offers"))
                   .build();
    }
}