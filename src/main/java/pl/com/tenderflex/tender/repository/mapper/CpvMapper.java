package pl.com.tenderflex.tender.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.tender.model.Cpv;

@Component
public class CpvMapper implements RowMapper<Cpv> {

    @Override
    public Cpv mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCpv(resultSet);
    }

    public Cpv mapCpv(ResultSet resultSet) throws SQLException {
        return Cpv
                 .builder()
                 .id(resultSet.getInt("cpv_id"))
                 .code(resultSet.getString("code"))
                 .description(resultSet.getString("description"))
                 .build();
    }

}