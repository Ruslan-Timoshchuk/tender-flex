package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.UserQueries.*;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.repository.extractor.UserExtractor;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserExtractor userExtractor;

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(jdbcTemplate.query(FIND_BY_EMAIL_QUERY, userExtractor, email));
    }

    @Override
    public User findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID_QUERY, userExtractor, id);
    }

}