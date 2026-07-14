package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.FileMetadataMixins.*;
import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.repository.FileMetadataRepository;
import com.flex.tender.repository.mapper.FileMetadataMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileMetadataRepositoryImpl implements FileMetadataRepository {

    private final JdbcTemplate jdbcTemplate;
    private final FileMetadataMapper fileMetadataMapper;

    @Override
    public FileMetadata save(FileMetadata file) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SAVE_NEW_QUERY, new String[] { "id" });
            statement.setString(1, file.getName());
            statement.setString(2, file.getContentType());
            statement.setString(3, file.getAwsS3fileKey());
            return statement;
        }, keyHolder);
        file.setId(keyHolder.getKeyAs(Integer.class));
        return file;
    }

    @Override
    public FileMetadata findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, fileMetadataMapper, id);
    }

}