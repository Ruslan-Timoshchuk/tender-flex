package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.CPV;

@Component
public class CpvMapper implements RowMapper<CPV>{

        @Override
        public CPV mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return CPV
                    .builder()
                    .id(resultSet.getInt("id"))
                    .code(resultSet.getString("code"))
                    .description(resultSet.getString("description"))
                    .build();
        }
    }