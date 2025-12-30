package com.flex.tender.repository.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    public static final String FIND_BY_EMAIL_QUERY = "SELECT id, first_name, last_name, email, password "
            + "FROM users WHERE email = ?";

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper userMapper;

    @Override
    public User findByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, userMapper, email);
    }
    
}