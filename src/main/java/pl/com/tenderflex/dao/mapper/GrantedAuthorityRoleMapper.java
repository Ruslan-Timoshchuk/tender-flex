package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

@Component
public class GrantedAuthorityRoleMapper implements RowMapper<GrantedAuthorityRole> {

    @Override
    public GrantedAuthorityRole mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return GrantedAuthorityRole
                .builder()
                .id(resultSet.getInt("id"))
                .role(ERole.valueOf(resultSet.getString("role")))
                .build();
    }
}