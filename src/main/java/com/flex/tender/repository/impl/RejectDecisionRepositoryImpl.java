package com.flex.tender.repository.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.repository.mapper.RejectDecisionMapper;
import com.flex.tender.repository.sql.mixins.FileMetadataMixins;
import com.flex.tender.repository.sql.mixins.RejectDecisionMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RejectDecisionRepositoryImpl implements RejectDecisionRepository {
 
    public static final String INSERT_QUERY = """
            INSERT INTO rejects(%s) 
            VALUES (%s)
            """.formatted(
                RejectDecisionMixins.REJECT_DECISION_INSERT_COLUMNS,
                RejectDecisionMixins.REJECT_DECISION_INSERT_VALUE_PARAMETERS);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s 
            FROM rejects reject_decision
            LEFT JOIN %s 
            WHERE reject_decision.id = :id
            """.formatted(
                RejectDecisionMixins.REJECT_DECISION_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_JOIN_REJECT_DECISIONS);
    public static final String FIND_BY_TENDER_ID_QUERY = """
            SELECT %s, %s 
            FROM rejects reject_decision
            LEFT JOIN %s 
            WHERE reject_decision.tender_id = :tenderId
            """.formatted(
                RejectDecisionMixins.REJECT_DECISION_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_JOIN_REJECT_DECISIONS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final RejectDecisionMapper rejectDecisionMapper;

    @Override
    public RejectDecision save(RejectDecision reject) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", reject.getTender().getId())
                .addValue("rejectFileId", reject.getFileMetadata().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        reject.setId(keyHolder.getKeyAs(Integer.class));
        return reject;
    }

    @Override
    public RejectDecision findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, rejectDecisionMapper);
    }

    @Override
    public RejectDecision findByTenderId(Integer tenderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId);
        return jdbc.queryForObject(FIND_BY_TENDER_ID_QUERY, parameters, rejectDecisionMapper);
    }
    
}