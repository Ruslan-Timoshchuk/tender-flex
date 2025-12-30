package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flex.tender.model.Authority;
import com.flex.tender.model.enumeration.ERole;
import com.flex.tender.repository.AuthorityRepository;
import com.flex.tender.repository.mapper.AuthorityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {

    public static final String SELECT_BY_ID_QUERY = """
            SELECT id, role FROM roles LEFT JOIN user_roles ur ON ur.role_id = id
            WHERE user_id = ?""";
    public static final String SELECT_BY_NAME = "SELECT id, role FROM roles WHERE role = ?";

    private final JdbcTemplate jdbcTemplate;
    private final AuthorityMapper roleMapper;

    @Override
    public List<Authority> findByUser(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_ID_QUERY, roleMapper, userId);
    }

    @Override
    public Authority findByName(ERole role) {
        return jdbcTemplate.queryForObject(SELECT_BY_NAME, roleMapper, role.name());
    }

}