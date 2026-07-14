package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.RejectDecisionColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.RejectDecision;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RejectDecisionMapper implements RowMapper<RejectDecision> {
    
    private final FileMetadataMapper fileMetadataMapper;
    
    @Override
    public RejectDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapReject(resultSet);
    }  
    
    public RejectDecision mapReject(ResultSet resultSet) throws SQLException {
        return RejectDecision
                .builder()
                .id(resultSet.getInt(REJECT_DECISION_ID))
                .fileMetadata(fileMetadataMapper.mapFileMetadata(resultSet))
                .build();
    }

}