package com.flex.tender.repository.impl;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Cpv;
import com.flex.tender.repository.CpvRepository;
import com.flex.tender.repository.extractor.TenderCpvExtractor;
import com.flex.tender.repository.mapper.CpvMapper;
import com.flex.tender.repository.sql.mixins.CpvMixins;
import com.flex.tender.repository.sql.mixins.TenderMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    public static final String FIND_ALL_QUERY = """
            SELECT %s 
            FROM cpvs cpv
            """.formatted(
                CpvMixins.CPV_QUERY_COLUMNS);
    public static final String FIND_BY_TENDER_ID_IN_QUERY = """
            SELECT %s, %s  
            FROM cpvs cpv 
            LEFT JOIN %s  
            WHERE tender.id IN (:tenderIds)
            """.formatted(
                CpvMixins.CPV_QUERY_COLUMNS,
                TenderMixins.TENDER_ID_QUERY_COLUMN,
                TenderMixins.TENDER_JOIN_CPVS);    
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s 
            FROM cpvs cpv 
            WHERE id = :id
            """.formatted(
                CpvMixins.CPV_QUERY_COLUMNS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final CpvMapper cpvMapper;
    private final TenderCpvExtractor tenderCpvExtractor;

    @Override
    public List<Cpv> findAll() {
        return jdbc.query(FIND_ALL_QUERY, cpvMapper);
    }
    
    @Override
    public Map<Integer, Cpv> findByTenderIdIn(List<Integer> tenderIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderIds", tenderIds);
        return jdbc.query(FIND_BY_TENDER_ID_IN_QUERY, parameters, tenderCpvExtractor);
    }

    @Override
    public Cpv findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, cpvMapper);
    }

}