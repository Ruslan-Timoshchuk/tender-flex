package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Cpv;

@Component
public class CpvMapper implements RowMapper<Cpv> {

    public static final String CPV_ID = "cpv_id";
    public static final String CPV_CODE = "cpv_code";
    public static final String CPV_SUMMARY = "cpv_summary";
    
    @Override
    public Cpv mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCpv(resultSet);
    }

    public Cpv mapCpv(ResultSet resultSet) throws SQLException {
        return Cpv
                 .builder()
                 .id(resultSet.getInt(CPV_ID))
                 .code(resultSet.getString(CPV_CODE))
                 .summary(resultSet.getString(CPV_SUMMARY))
                 .build();
    }

}