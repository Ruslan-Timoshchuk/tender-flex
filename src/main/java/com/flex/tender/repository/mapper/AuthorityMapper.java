package com.flex.tender.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.ERole;

@Component
public class AuthorityMapper implements RowMapper<Authority> {

    public static final String AUTHORITY_ID = "id";
    public static final String AUTHORITY_ROLE = "role";
    
    @Override
    public Authority mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Authority
                .builder()
                .id(resultSet.getInt(AUTHORITY_ID))
                .role(ERole.valueOf(resultSet.getString(AUTHORITY_ROLE)))
                .build();
    }
    
}