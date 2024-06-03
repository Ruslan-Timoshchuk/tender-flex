package pl.com.tenderflex.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.UserRepository;
import pl.com.tenderflex.dao.mapper.UserMapper;
import pl.com.tenderflex.security.impl.UserDetailsImpl;

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
    public UserDetailsImpl getByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_QUERY, userMapper, email);
    }
    
    public UserDetailsImpl getById(Integer email) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, userMapper, email);
    }
}