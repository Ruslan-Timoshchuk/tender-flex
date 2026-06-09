package com.flex.tender.repository.extractor;

import static com.flex.tender.repository.sql.column.TenderColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Offer;
import com.flex.tender.repository.mapper.OfferMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TenderOfferMapExtractor implements ResultSetExtractor<Map<Integer, Offer>> {

    private final OfferMapper offerMapper;

    @Override
    public Map<Integer, Offer> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        final var result = new HashMap<Integer, Offer>();
        while (resultSet.next()) {
            Integer tenderId = resultSet.getObject(TENDER_ID, Integer.class);
            Offer offer = offerMapper.mapOffer(resultSet);
            result.put(tenderId, offer);
        }
        return result;
    }

}