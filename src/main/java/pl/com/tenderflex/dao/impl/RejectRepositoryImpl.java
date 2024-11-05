package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RejectRepository;
import pl.com.tenderflex.model.RejectDecision;

@Repository
@RequiredArgsConstructor
public class RejectRepositoryImpl implements RejectRepository {

    public static final String ADD_NEW_REJECT_QUERY = "INSERT INTO rejects(tender_id, reject_file_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public RejectDecision save(RejectDecision reject) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_REJECT_QUERY, new String[] { "id" });
            statement.setInt(1, reject.getTender().getId());
            statement.setInt(2, reject.getRejectFile().getId());
            return statement;
        }, keyHolder);
        reject.setId(keyHolder.getKeyAs(Integer.class));
        return reject;
    }

}