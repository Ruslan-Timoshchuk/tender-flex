package com.flex.tender.repository.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.repository.mapper.AwardDecisionMapper;
import com.flex.tender.repository.sql.query.AwardDecisionMixins;
import com.flex.tender.repository.sql.query.FileMetadataMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AwardDecisionRepositoryImpl implements AwardDecisionRepository {

    public static final String INSERT_QUERY = """
            INSERT INTO awards(%s) 
            VALUES (%s)
            """.formatted(
                AwardDecisionMixins.AWARD_DECISION_INSERT_COLUMNS,
                AwardDecisionMixins.AWARD_DECISION_INSERT_VALUE_PARAMETERS);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s 
            FROM awards award_decision
            LEFT JOIN %s 
            WHERE award_decision.id = :id
            """.formatted(
                AwardDecisionMixins.AWARD_DECISION_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_JOIN_AWARD_DECISIONS);
    public static final String FIND_BY_TENDER_ID_QUERY = """
            SELECT %s, %s 
            FROM awards award_decision
            LEFT JOIN %s 
            WHERE award_decision.tender_id = :tenderId
            """.formatted(
                AwardDecisionMixins.AWARD_DECISION_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_JOIN_AWARD_DECISIONS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final AwardDecisionMapper awardDecisionMapper;

    @Override
    public AwardDecision save(AwardDecision awardDecision) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", awardDecision.getTender().getId())
                .addValue("awardFileId", awardDecision.getFileMetadata().getId());
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        awardDecision.setId(keyHolder.getKeyAs(Integer.class));
        return awardDecision;
    }

    @Override
    public AwardDecision findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, awardDecisionMapper);
    }

    @Override
    public AwardDecision findByTenderId(Integer tenderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId);
        return jdbc.queryForObject(FIND_BY_TENDER_ID_QUERY, parameters, awardDecisionMapper);
    }

}