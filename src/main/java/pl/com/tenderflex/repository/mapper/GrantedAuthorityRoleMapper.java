package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

@Component
public class GrantedAuthorityRoleMapper implements RowMapper<GrantedAuthorityRole> {

    public static final String AUTHORITY_ID = "id";
    public static final String AUTHORITY_ROLE = "role";
    
    @Override
    public GrantedAuthorityRole mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return GrantedAuthorityRole
                .builder()
                .id(resultSet.getInt(AUTHORITY_ID))
                .role(ERole.valueOf(resultSet.getString(AUTHORITY_ROLE)))
                .build();
    }
    
}