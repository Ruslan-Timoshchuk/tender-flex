package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.FileMetadataColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import com.flex.tender.model.FileMetadata;

@Component
public class FileMeatadataMapper {
    
    public FileMetadata mapFileMetadata(ResultSet resultSet) throws SQLException {
        return FileMetadata
                .builder()
                .id(resultSet.getInt(FILE_METADATA_ID))
                .name(resultSet.getString(FILE_METADATA_NAME))
                .contentType(resultSet.getString(FILE_METADATA_CONTENT_TYPE))
                .awsS3fileKey(resultSet.getString(FILE_METADATA_AWS3_KEY))
                .build();
    }
    
}