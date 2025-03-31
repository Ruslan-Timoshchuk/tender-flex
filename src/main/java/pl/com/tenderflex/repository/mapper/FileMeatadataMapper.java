package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.FileMetadata;

@Component
public class FileMeatadataMapper {

    public static final String FILE_ID = "id";
    public static final String FILE_NAME = "name";
    public static final String FILE_CONTENT_TYPE = "content_type";
    public static final String FILE_AWS3_KEY = "aws_s3_file_key";
    
    public FileMetadata mapFileMetadata(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return FileMetadata
                .builder()
                .id(resultSet.getInt(columnLabels.get(FILE_ID)))
                .name(resultSet.getString(columnLabels.get(FILE_NAME)))
                .contentType(resultSet.getString(columnLabels.get(FILE_CONTENT_TYPE)))
                .awsS3fileKey(resultSet.getString(columnLabels.get(FILE_AWS3_KEY)))
                .build();
    }
    
}