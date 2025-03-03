package pl.com.tenderflex.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.FileMetadata;
import pl.com.tenderflex.repository.FileRepository;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    public static final String ADD_NEW_FILE_QUERY = "INSERT INTO files(name, content_type, aws_s3_file_key) "
            + "VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public FileMetadata save(FileMetadata file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_FILE_QUERY, new String[] { "id" });
            statement.setString(1, file.getName());
            statement.setString(2, file.getContentType());
            statement.setString(3, file.getAwsS3fileKey());
            return statement;
        }, keyHolder);
        file.setId(keyHolder.getKeyAs(Integer.class));
        return file;
    }

}