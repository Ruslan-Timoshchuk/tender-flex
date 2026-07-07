package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.AuthorityColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;

@Component
public class AuthorityMapper implements RowMapper<Authority> {

    @Override
    public Authority mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapSingle(resultSet);
    }

    public Authority mapSingle(ResultSet resultSet) throws SQLException {
        return Authority.builder()
                        .id(resultSet.getInt(AUTHORITY_ID))
                        .title(EAuthority.valueOf(resultSet.getString(AUTHORITY_TITLE)))
                        .build();
    }

}