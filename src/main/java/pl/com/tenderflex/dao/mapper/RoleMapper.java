package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ERole;
import pl.com.tenderflex.model.Role;

@Component
public class RoleMapper implements RowMapper<Role> {
    
    @Override
    public Role mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(resultSet.getInt("id"));
        role.setName(ERole.valueOf(resultSet.getString("name")));
        return role;
    }
}