package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.AwardDecisionQueries.*;
import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.repository.AwardDecisionRepository;
import com.flex.tender.repository.mapper.AwardDecisionMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AwardDecisionRepositoryImpl implements AwardDecisionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final AwardDecisionMapper awardDecisionMapper;

    @Override
    public AwardDecision save(AwardDecision award) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_AWARD_DECISION_QUERY,
                    new String[] { "id" });
            statement.setInt(1, award.getTender().getId());
            statement.setInt(2, award.getFileMetadata().getId());
            return statement;
        }, keyHolder);
        award.setId(keyHolder.getKeyAs(Integer.class));
        return award;
    }

    @Override
    public AwardDecision findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, awardDecisionMapper, id);
    }

    @Override
    public AwardDecision findByTenderId(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_TENDER_ID_QUERY, awardDecisionMapper, id);
    }

}