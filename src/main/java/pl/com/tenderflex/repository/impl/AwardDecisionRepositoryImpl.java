package pl.com.tenderflex.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.repository.AwardDecisionRepository;
import pl.com.tenderflex.repository.mapper.AwardDecisionMapper;

@Repository
@RequiredArgsConstructor
public class AwardDecisionRepositoryImpl implements AwardDecisionRepository {

    public static final String ADD_NEW_AWARD_DECISION_QUERY = "INSERT INTO awards(tender_id, award_file_id) VALUES (?, ?)";
    public static final String SELECT_BY_ID_QUERY = """
            SELECT award.id AS award_id, award_file.id AS award_file_id, award_file.name AS award_file_name,
            award_file.content_type AS award_file_content_type, award_file.aws_s3_file_key AS award_aws_s3_file_key
            FROM awards award
            LEFT JOIN files award_file ON award_file.id = award.award_file_id
            WHERE award.id = ?
            """;

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
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, awardDecisionMapper, id);
    }

}