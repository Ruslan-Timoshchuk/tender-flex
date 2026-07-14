package com.flex.tender.repository.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.FileMetadata;
import com.flex.tender.repository.FileMetadataRepository;
import com.flex.tender.repository.mapper.FileMetadataMapper;
import com.flex.tender.repository.sql.query.FileMetadataMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FileMetadataRepositoryImpl implements FileMetadataRepository {

    public static final String INSERT_QUERY = """
            INSERT INTO files(%s)
            VALUES (%s)
            """.formatted(
                FileMetadataMixins.FILE_METADATA_INSERT_COLUMNS,
                FileMetadataMixins.FILE_METADATA_VALUE_PARAMETERS);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s  
            FROM files file_metadata 
            WHERE id = :id
            """.formatted(
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS);
     
    private final NamedParameterJdbcTemplate jdbc;
    private final FileMetadataMapper fileMetadataMapper;

    @Override
    public FileMetadata save(FileMetadata file) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", file.getName())
                .addValue("contentType", file.getContentType())
                .addValue("awsS3FileKey", file.getAwsS3fileKey());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        file.setId(keyHolder.getKeyAs(Integer.class));
        return file;
    }

    @Override
    public FileMetadata findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, fileMetadataMapper);
    }

}