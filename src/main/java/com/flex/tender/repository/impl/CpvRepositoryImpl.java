package com.flex.tender.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flex.tender.model.Cpv;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.repository.mapper.CpvMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpvRepositoryImpl.class);
    
    public static final String FIND_ALL_CPVS_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs";
      
    private final JdbcTemplate jdbcTemplate;
    private final CpvMapper cpvMapper;

    @Override
    public List<Cpv> findAll() {
        List<Cpv> cpvs = jdbcTemplate.query(FIND_ALL_CPVS_QUERY, cpvMapper);
        LOGGER.info("Successfully fetched {} cpvs", cpvs.size());
        return cpvs;
    }

}