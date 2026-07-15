package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Authority;
import com.flex.tender.repository.AuthorityRepository;
import com.flex.tender.repository.mapper.AuthorityMapper;
import com.flex.tender.repository.sql.mixins.AuthorityMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorityRepositoryImpl implements AuthorityRepository {

    public static final String FIND_ALL_QUERY = """
           SELECT %s
           FROM authorities authority
           """.formatted(
               AuthorityMixins.AUTHORITY_QUERY_COLUMNS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final AuthorityMapper authorityMapper;

    @Override
    public List<Authority> findAll() {
        return jdbc.query(FIND_ALL_QUERY, authorityMapper);
    }

}