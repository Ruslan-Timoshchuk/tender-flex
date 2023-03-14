package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RoleRepository;
import pl.com.tenderflex.model.User;

@Component
@RequiredArgsConstructor
public class UserMapper implements RowMapper<User> {

    private final RoleRepository roleRepository;
    
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        Integer userId = resultSet.getInt("id");
        user.setId(userId);
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setRoles(roleRepository.getByUser(userId));
        return user;
    }
}