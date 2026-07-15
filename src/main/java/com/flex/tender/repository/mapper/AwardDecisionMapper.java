package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.AwardDecisionColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.AwardDecision;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AwardDecisionMapper implements RowMapper<AwardDecision> {
    
    private final FileMetadataMapper fileMapper;

    @Override
    public AwardDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapAward(resultSet);
    } 
    
    public AwardDecision mapAward(ResultSet resultSet) throws SQLException {
        return AwardDecision.builder()
                            .id(resultSet.getInt(AWARD_DECISION_ID))
                            .fileMetadata(fileMapper.mapFileMetadata(resultSet))
               .build();
    }
 
}