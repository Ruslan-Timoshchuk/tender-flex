package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.RejectDecision;

@Component
@RequiredArgsConstructor
public class RejectMapper implements RowMapper<RejectDecision>{

    private final FileMapper fileMapper;
    
    @Override
    public RejectDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return RejectDecision
                .builder()
                .id(resultSet.getInt("reject_id"))
                .rejectFile(fileMapper.mapRow(resultSet, rowNum))
                .build();
    }  
    
}