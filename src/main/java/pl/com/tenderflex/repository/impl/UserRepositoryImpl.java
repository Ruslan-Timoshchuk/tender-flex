package pl.com.tenderflex.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.User;
import pl.com.tenderflex.repository.UserRepository;
import pl.com.tenderflex.repository.mapper.UserMapper;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    public static final String GET_USER_BY_EMAIL_QUERY = "SELECT id, first_name, last_name, email, password "
            + "FROM users WHERE email = ?";
    
    public static final String GET_USER_BY_ID_QUERY = "SELECT id, first_name, last_name, email, password "
            + "FROM users WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_QUERY, userMapper, email);
    }
    
}