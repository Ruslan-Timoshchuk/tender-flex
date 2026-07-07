package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.mapper.FileMeatadataMapper.*;
import static com.flex.tender.repository.sql.column.OfferColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Offer;
import com.flex.tender.model.enumeration.EOfferStatus;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    
    private final CompanyProfileMapper companyProfileMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
    
    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
       return mapOffer(resultSet);
    }
    
    public Offer mapOffer(ResultSet resultSet) throws SQLException {
        return Offer
                .builder()
                .id(resultSet.getInt(OFFER_ID))       
                .companyProfile(companyProfileMapper.mapCompanyProfile(resultSet))
                .globalStatus(EOfferStatus.valueOf(resultSet.getString(GLOBAL_STATUS)))
                .bidPrice(resultSet.getInt(BID_PRICE))
                .currency(currencyMapper.mapCurrency(resultSet))
                .publication(resultSet.getObject(PUBLICATION_DATE, LocalDate.class))
                .proposition(fileMeatadataMapper.
                        mapFileMetadata(resultSet, 
                                Map.of(FILE_ID, PROPOSITION_FILE_ID,
                                       FILE_NAME, PROPOSITION_FILE_NAME,
                                       FILE_CONTENT_TYPE, PROPOSITION_FILE_CONTENT_TYPE,
                                       FILE_AWS3_KEY, PROPOSITION_FILE_AWS3_KEY)))
              .build();
    }
    
}