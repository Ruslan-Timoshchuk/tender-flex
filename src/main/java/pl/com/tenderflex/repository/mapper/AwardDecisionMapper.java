package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;

@Component
@RequiredArgsConstructor
public class AwardDecisionMapper implements RowMapper<AwardDecision> {

    public static final String AWARD_DECISION_ID = "award_id";
    public static final String AWARD_FILE_ID = "award_file_id";
    public static final String AWARD_FILE_NAME = "award_file_name";
    public static final String AWARD_FILE_CONTENT_TYPE = "award_file_content_type";
    public static final String AWARD_FILE_AWS3_KEY = "award_aws_s3_file_key";
    
    private final FileMeatadataMapper fileMapper;

    @Override
    public AwardDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapAward(resultSet);
    } 
    
    public AwardDecision mapAward(ResultSet resultSet) throws SQLException {
        return AwardDecision
                 .builder()
                 .id(resultSet.getInt(AWARD_DECISION_ID))
                 .fileMetadata(fileMapper.mapFileMetadata(resultSet, 
                         Map.of(FILE_ID, AWARD_FILE_ID, 
                                FILE_NAME, AWARD_FILE_NAME, 
                                FILE_CONTENT_TYPE, AWARD_FILE_CONTENT_TYPE,
                                FILE_AWS3_KEY, AWARD_FILE_AWS3_KEY)))
                 .build();
    }
 
}