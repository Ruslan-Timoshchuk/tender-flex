package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.AwardRepository;
import pl.com.tenderflex.model.AwardDecision;

@Repository
@RequiredArgsConstructor
public class AwardRepositoryImpl implements AwardRepository {

    public static final String ADD_NEW_AWARD_QUERY = "INSERT INTO awards(tender_id, award_file_id) VALUES (?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public AwardDecision save(AwardDecision award) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_AWARD_QUERY, new String[] { "id" });
            statement.setInt(1, award.getTender().getId());
            statement.setInt(2, award.getAwardFile().getId());
            return statement;
        }, keyHolder);
        award.setId(keyHolder.getKeyAs(Integer.class));
        return award;
    }

}