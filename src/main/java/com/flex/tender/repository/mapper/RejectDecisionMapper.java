package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.RejectDecisionColumns.*;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.FILE_AWS3_KEY;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.FILE_CONTENT_TYPE;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.FILE_ID;
import static com.flex.tender.repository.mapper.FileMeatadataMapper.FILE_NAME;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.RejectDecision;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RejectDecisionMapper implements RowMapper<RejectDecision> {
    
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