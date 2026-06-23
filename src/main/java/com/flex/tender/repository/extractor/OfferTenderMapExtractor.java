package com.flex.tender.repository.extractor;

import static com.flex.tender.repository.sql.column.OfferColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Tender;
import com.flex.tender.repository.mapper.TenderMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OfferTenderMapExtractor implements ResultSetExtractor<Map<Integer, Tender>> {

    private final TenderMapper tenderMapper;

    @Override
    public Map<Integer, Tender> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        final var result = new HashMap<Integer, Tender>();
        while (resultSet.next()) {
            Integer offerId = resultSet.getObject(OFFER_ID, Integer.class);
            Tender tender = tenderMapper.mapTender(resultSet);
            result.put(offerId, tender);
        }
        return result;
    }

}