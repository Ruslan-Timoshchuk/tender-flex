package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.EOfferStatus;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    
    public static final String OFFER_ID = "id";
    public static final String TENDER_ID = "tender_id";
    public static final String GLOBAL_STATUS = "global_status"; 
    public static final String BID_PRICE = "bid_price";  
    public static final String PUBLICATION_DATE = "publication_date";             
    public static final String PROPOSITION_FILE_ID = "proposition_file_id";
    public static final String PROPOSITION_FILE_NAME = "proposition_file_name";
    public static final String PROPOSITION_FILE_CONTENT_TYPE = "proposition_file_content_type";
    public static final String PROPOSITION_FILE_AWS3_KEY = "proposition_file_aws_s3_file_key"; 
    
    private final CompanyProfileMapper companyProfileMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
    
    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Offer
                 .builder()
                 .id(resultSet.getInt(OFFER_ID))       
                 .tender(Tender
                           .builder()
                           .id(resultSet.getInt(TENDER_ID))
                           .build())
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