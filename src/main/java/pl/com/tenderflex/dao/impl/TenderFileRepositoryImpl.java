package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.TenderFileRepository;
import pl.com.tenderflex.model.TenderFile;

@RequiredArgsConstructor
public class TenderFileRepositoryImpl implements TenderFileRepository {

    public static final String ADD_NEW_TENDER_FILE_QUERY = "INSERT INTO tender_files(file_name, type, content_type, aws_file_key) "
            + "VALUES (?, ?, ?, ?)";
    
    private final JdbcTemplate jdbcTemplate;
    
    @Override
    public TenderFile create(TenderFile tenderFile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_FILE_QUERY, new String[] { "id" });
            statement.setString(1, tenderFile.getName());
            statement.setString(2, tenderFile.getType().name());
            statement.setString(3, tenderFile.getContentType());
            statement.setString(4, tenderFile.getAwsS3fileKey());
            return statement;
        }, keyHolder);
        tenderFile.setId(keyHolder.getKeyAs(Integer.class));
        return tenderFile;
    }
}