package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Cpv;

@Component
public class CpvMapper implements RowMapper<Cpv>{

        @Override
        public Cpv mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return Cpv
                    .builder()
                    .id(resultSet.getInt("id"))
                    .code(resultSet.getString("code"))
                    .description(resultSet.getString("description"))
                    .build();
        }
    }