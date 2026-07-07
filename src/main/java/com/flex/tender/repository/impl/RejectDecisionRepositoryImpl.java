package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.RejectDecisionQueries.*;
import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.repository.RejectDecisionRepository;
import com.flex.tender.repository.mapper.RejectDecisionMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RejectDecisionRepositoryImpl implements RejectDecisionRepository {
 
    private final JdbcTemplate jdbcTemplate;
    private final RejectDecisionMapper rejectDecisionMapper;

    @Override
    public RejectDecision save(RejectDecision reject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_REJECT_QUERY, new String[] { "id" });
            statement.setInt(1, reject.getTender().getId());
            statement.setInt(2, reject.getFileMetadata().getId());
            return statement;
        }, keyHolder);
        reject.setId(keyHolder.getKeyAs(Integer.class));
        return reject;
    }

    @Override
    public RejectDecision findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, rejectDecisionMapper, id);
    }

    @Override
    public RejectDecision findByTenderId(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_TENDER_ID_QUERY, rejectDecisionMapper, id);
    }
    
}