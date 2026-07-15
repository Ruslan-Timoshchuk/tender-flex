package com.flex.tender.repository.impl;

import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.User;
import com.flex.tender.repository.UserRepository;
import com.flex.tender.repository.extractor.UserExtractor;
import com.flex.tender.repository.sql.mixins.AuthorityMixins;
import com.flex.tender.repository.sql.mixins.UserMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    public static final String FIND_BY_EMAIL_QUERY = """
            SELECT %s, %s 
            FROM users usr 
            LEFT JOIN %s 
            LEFT JOIN %s 
            WHERE usr.email = :email
            """.formatted(
                UserMixins.USER_QUERY_COLUMNS,
                AuthorityMixins.AUTHORITY_QUERY_COLUMNS,
                AuthorityMixins.USER_AUTHORITY_JOIN_USERS,
                AuthorityMixins.AUTHORITY_JOIN_USERS_AUTHORITIES);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s
            FROM users usr 
            LEFT JOIN %s  
            LEFT JOIN %s 
            WHERE usr.id = :id
            """.formatted(
                    UserMixins.USER_QUERY_COLUMNS,
                    AuthorityMixins.AUTHORITY_QUERY_COLUMNS,
                    AuthorityMixins.USER_AUTHORITY_JOIN_USERS,
                    AuthorityMixins.AUTHORITY_JOIN_USERS_AUTHORITIES);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final UserExtractor userExtractor;

    @Override
    public Optional<User> findByEmail(String email) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", email);
        return Optional.ofNullable(jdbc.query(FIND_BY_EMAIL_QUERY, parameters, userExtractor));
    }

    @Override
    public User findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.query(FIND_BY_ID_QUERY, parameters, userExtractor);
    }

}