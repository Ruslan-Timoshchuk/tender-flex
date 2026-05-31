package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.AwardDecisionColumns.*;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.AwardDecision;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwardDecisionMapper implements RowMapper<AwardDecision> {
    
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