package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.FileRepository;
import pl.com.tenderflex.model.File;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {

    public static final String ADD_NEW_FILE_QUERY = "INSERT INTO files(name, file_type, content_type, aws_s3_file_key) "
            + "VALUES (?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public File create(File file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_FILE_QUERY, new String[] { "id" });
            statement.setString(1, file.getName());
            statement.setString(2, file.getFileType().name());
            statement.setString(3, file.getContentType());
            statement.setString(4, file.getAwsS3fileKey());
            return statement;
        }, keyHolder);
        file.setId(keyHolder.getKeyAs(Integer.class));
        return file;
    }

}