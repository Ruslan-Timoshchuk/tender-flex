package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.impl.CpvRepositoryImpl.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Cpv;

@Component
public class CpvMapper implements RowMapper<Cpv> {

    @Override
    public Cpv mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCpv(resultSet, 
                Map.of(CPV_ID, CPV_ID,
                       CPV_CODE, CPV_CODE,
                       CPV_DESCRIPTION, CPV_DESCRIPTION));
    }

    public Cpv mapCpv(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return Cpv
                 .builder()
                 .id(resultSet.getInt(columnLabels.get(CPV_ID)))
                 .code(resultSet.getString(columnLabels.get(CPV_CODE)))
                 .description(resultSet.getString(columnLabels.get(CPV_DESCRIPTION)))
                 .build();
    }

}