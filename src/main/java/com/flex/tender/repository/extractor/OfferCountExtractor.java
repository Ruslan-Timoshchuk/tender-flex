package com.flex.tender.repository.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class OfferCountExtractor implements ResultSetExtractor<Map<Integer, Integer>> {

    @Override
    public Map<Integer, Integer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        final var result = new HashMap<Integer, Integer>();
        while (resultSet.next()) {
            Integer tenderId = resultSet.getObject("tender_id", Integer.class);
            Integer offers = Math.toIntExact(resultSet.getLong("offers"));
            result.put(tenderId, offers);
        }
        return result;
    }

}