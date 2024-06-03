package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.security.impl.UserDetailsImpl;

@Component
public class UserMapper implements RowMapper<UserDetailsImpl> {

    @Override
    public UserDetailsImpl mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return UserDetailsImpl
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .build();
    }
}