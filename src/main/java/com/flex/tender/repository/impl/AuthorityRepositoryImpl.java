package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.AuthorityQueries.*;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Authority;
import com.flex.tender.repository.AuthorityRepository;
import com.flex.tender.repository.mapper.AuthorityMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AuthorityMapper authorityMapper;

    @Override
    public List<Authority> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, authorityMapper);
    }

}