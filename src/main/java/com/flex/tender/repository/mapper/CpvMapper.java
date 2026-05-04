package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.CpvColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Cpv;

@Component
public class CpvMapper implements RowMapper<Cpv> {
    
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