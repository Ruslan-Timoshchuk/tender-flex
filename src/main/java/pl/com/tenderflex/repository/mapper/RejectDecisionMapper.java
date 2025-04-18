package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.FILE_AWS3_KEY;
import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.FILE_CONTENT_TYPE;
import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.FILE_ID;
import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.FILE_NAME;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.RejectDecision;

@Component
@RequiredArgsConstructor
public class RejectDecisionMapper implements RowMapper<RejectDecision> {

    public static final String REJECT_DECISION_ID = "reject_id";
    public static final String REJECT_FILE_ID = "reject_file_id";
    public static final String REJECT_FILE_NAME = "reject_file_name";
    public static final String REJECT_FILE_CONTENT_TYPE = "reject_file_content_type";
    public static final String REJECT_FILE_AWS3_KEY = "reject_aws_s3_file_key";
    
    private final FileMeatadataMapper fileMapper;
    
    @Override
    public RejectDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapReject(resultSet);
    }  
    
    public RejectDecision mapReject(ResultSet resultSet) throws SQLException {
        return RejectDecision
                .builder()
                .id(resultSet.getInt(REJECT_DECISION_ID))
                .fileMetadata(fileMapper.mapFileMetadata(resultSet, 
                        Map.of(FILE_ID, REJECT_FILE_ID, 
                               FILE_NAME, REJECT_FILE_NAME, 
                               FILE_CONTENT_TYPE, REJECT_FILE_CONTENT_TYPE,
                               FILE_AWS3_KEY, REJECT_FILE_AWS3_KEY)))
                .build();
    }

}