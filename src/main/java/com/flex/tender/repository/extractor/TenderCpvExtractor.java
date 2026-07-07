package com.flex.tender.repository.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Cpv;
import com.flex.tender.repository.mapper.CpvMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenderCpvExtractor implements ResultSetExtractor<Map<Integer, Cpv>> {

    private final CpvMapper cpvMapper;

    @Override
    public Map<Integer, Cpv> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        var result = new HashMap<Integer, Cpv>();
        while (resultSet.next()) {
            result.put(resultSet.getObject("tender_id", Integer.class), cpvMapper.mapCpv(resultSet));
        }
        return result;
    }

}