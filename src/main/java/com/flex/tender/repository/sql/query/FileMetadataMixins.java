package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FileMetadataMixins {

    public final String FILE_METADATA_QUERY_COLUMNS = """
            file_metadata.id AS file_metadata_id,
            file_metadata.name AS file_metadata_name, 
            file_metadata.content_type AS file_metadata_content_type,
            file_metadata.aws_s3_file_key AS file_metadata_aws_s3_file_key
            """;
    public final String FILE_METADATA_JOIN_OFFERS = """
            files file_metadata ON 
            file_metadata.id = offer.proposition_file_id""";
    public final String SAVE_NEW_QUERY = "INSERT INTO files(name, content_type, aws_s3_file_key) VALUES (?, ?, ?)";
    public final String FIND_BY_ID_QUERY = "SELECT id, name, content_type, aws_s3_file_key FROM files WHERE id = ?";
    
}