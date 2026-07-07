package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.EAuthority;
import com.flex.tender.repository.AuthorityRepository;
import com.flex.tender.repository.mapper.AuthorityMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {

    public static final String FIND_ALL_QUERY = "SELECT id, title FROM authorities";
    public static final String SELECT_BY_NAME = "SELECT id, title FROM authorities WHERE title = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AuthorityMapper authorityMapper;

    @Override
    public List<Authority> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, authorityMapper);
    }

    @Override
    public Authority findByName(EAuthority authority) {
        return jdbcTemplate.queryForObject(SELECT_BY_NAME, authorityMapper, authority.name());
    }

}