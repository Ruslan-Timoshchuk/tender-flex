package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;

@Component
@RequiredArgsConstructor
public class AwardMapper implements RowMapper<AwardDecision>{

    private final FileMapper fileMapper;
    
    @Override
    public AwardDecision mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return AwardDecision
                 .builder()
                 .id(resultSet.getInt("award_id"))
                 .awardFile(fileMapper.mapRow(resultSet, rowNum))
                 .build();
    }  
    
}