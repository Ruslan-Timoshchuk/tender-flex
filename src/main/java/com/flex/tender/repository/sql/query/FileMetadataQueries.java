package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileMetadataQueries {

    public final String SAVE_NEW_QUERY = "INSERT INTO files(name, content_type, aws_s3_file_key) VALUES (?, ?, ?)";
    public final String FIND_BY_ID_QUERY = "SELECT id, name, content_type, aws_s3_file_key FROM files WHERE id = ?";
    
}