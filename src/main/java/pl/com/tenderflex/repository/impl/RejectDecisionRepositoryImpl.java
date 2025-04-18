package pl.com.tenderflex.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.repository.RejectDecisionRepository;
import pl.com.tenderflex.repository.mapper.RejectDecisionMapper;

@Repository
@RequiredArgsConstructor
public class RejectDecisionRepositoryImpl implements RejectDecisionRepository {

    public static final String ADD_NEW_REJECT_QUERY = "INSERT INTO rejects(tender_id, reject_file_id) VALUES (?, ?)";
    public static final String SELECT_REJECT_BY_ID_QUERY = """
            SELECT reject.id AS reject_id, reject_file.id AS reject_file_id, reject_file.name AS reject_file_name,
            reject_file.content_type AS reject_file_content_type, reject_file.aws_s3_file_key AS reject_aws_s3_file_key
            FROM rejects reject
            LEFT JOIN files reject_file ON reject_file.id = reject.reject_file_id
            WHERE reject.id = ?
            """;
 
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
        return jdbcTemplate.queryForObject(SELECT_REJECT_BY_ID_QUERY, rejectDecisionMapper, id);
    }
    
}