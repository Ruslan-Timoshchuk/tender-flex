package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.User;

@Component
public class UserMapper implements RowMapper<User> {

    public static final String USER_ID = "id";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PASSWORD = "password";
    
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return User
                .builder()
                .id(resultSet.getInt(USER_ID))
                .firstName(resultSet.getString(USER_FIRST_NAME))
                .lastName(resultSet.getString(USER_LAST_NAME))
                .email(resultSet.getString(USER_EMAIL))
                .password(resultSet.getString(USER_PASSWORD))
                .build();
    }
    
}