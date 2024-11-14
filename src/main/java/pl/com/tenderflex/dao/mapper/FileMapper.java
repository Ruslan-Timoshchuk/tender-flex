package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.File;

@Component
public class FileMapper implements RowMapper<File>{

    @Override
    public File mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return File
                .builder()
                .id(resultSet.getInt("file_id"))
                .name(resultSet.getString("name"))
                .contentType(resultSet.getString("content_type"))
                .awsS3fileKey(resultSet.getString("aws_s3_file_key"))
                .build();
    }  
    
}