package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.CompanyProfileMapper.*;
import static pl.com.tenderflex.repository.mapper.CountryMapper.*;
import static pl.com.tenderflex.repository.mapper.CurrencyMapper.*;
import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.enums.EOfferStatus;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {
    
    // Offer
    public static final String OFFER_ID = "offer_id";
    public static final String OFFER_GLOBAL_STATUS = "offer_global_status"; 
    public static final String OFFER_BID_PRICE = "offer_bid_price";  
    public static final String OFFER_PUBLICATION_DATE = "offer_publication_date";
    
    // Bidder country of Company Profile
    public static final String BIDDER_PROFILE_COUNTRY_ID = "bidder_country_id";
    public static final String BIDDER_PROFILE_COUNTRY_NAME = "bidder_country_name";
    public static final String BIDDER_PROFILE_COUNTRY_ISO_CODE = "bidder_country_iso_code";
    public static final String BIDDER_PROFILE_COUNTRY_PHONE_CODE = "bidder_country_phone_code";
    
    // Bidder Company Profile
    public static final String BIDDER_PROFILE_ID = "bidder_profile_id";
    public static final String BIDDER_PROFILE_OFFICIAL_NAME = "bidder_official_name";
    public static final String BIDDER_PROFILE_REGISTRATION_NUMBER = "bidder_registration_number";
    public static final String BIDDER_PROFILE_CITY = "bidder_city";
        
    // Bidder Contact Person
    public static final String BIDDER_CONTACT_FIRST_NAME = "bidder_contact_first_name";
    public static final String BIDDER_CONTACT_LAST_NAME = "bidder_contact_last_name";
    public static final String BIDDER_CONTACT_PHONE_NUMBER = "bidder_contact_phone_number";
    
    // Offer currency
    public static final String OFFER_CURRENCY_ID = "offer_currency_id";
    public static final String OFFER_CURRENCY_CODE = "offer_currency_code";
    public static final String OFFER_CURRENCY_SYMBOL = "offer_currency_symbol";
    
    // Proposition file metadata
    public static final String OFFER_FILE_ID = "proposition_file_id";
    public static final String OFFER_FILE_NAME = "proposition_file_name";
    public static final String OFFER_FILE_CONTENT_TYPE = "proposition_file_content_type";
    public static final String OFFER_FILE_AWS3_KEY = "proposition_aws_s3_file_key"; 
    
    private final TenderMapper tenderMapper;
    private final CountryMapper countryMapper;
    private final CompanyProfileMapper companyProfileMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
    
    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        CompanyProfile companyProfile = companyProfileMapper
                .mapCompanyProfile(countryMapper.mapCountry(
                        resultSet, Map.of(COUNTRY_ID, BIDDER_PROFILE_COUNTRY_ID, 
                                          COUNTRY_NAME, BIDDER_PROFILE_COUNTRY_NAME,
                                          COUNTRY_ISO_CODE, BIDDER_PROFILE_COUNTRY_ISO_CODE, 
                                          COUNTRY_PHONE_CODE, BIDDER_PROFILE_COUNTRY_PHONE_CODE)), 
                        resultSet, Map.of(PROFILE_ID, BIDDER_PROFILE_ID, 
                                          PROFILE_OFFICIAL_NAME, BIDDER_PROFILE_OFFICIAL_NAME,
                                          PROFILE_REGISTRATION_NUMBER, BIDDER_PROFILE_REGISTRATION_NUMBER, 
                                          PROFILE_CITY, BIDDER_PROFILE_CITY, 
                                          CONTACT_FIRST_NAME, BIDDER_CONTACT_FIRST_NAME, 
                                          CONTACT_LAST_NAME, BIDDER_CONTACT_LAST_NAME, 
                                          CONTACT_PHONE_NUMBER, BIDDER_CONTACT_PHONE_NUMBER));
        return Offer
                 .builder()
                 .id(resultSet.getInt(OFFER_ID))   
                 .tender(tenderMapper.mapTender(resultSet))
                 .companyProfile(companyProfile)
                 .globalStatus(EOfferStatus.valueOf(resultSet.getString(OFFER_GLOBAL_STATUS)))
                 .bidPrice(resultSet.getInt(OFFER_BID_PRICE))
                 .currency(currencyMapper.mapCurrency(resultSet, 
                         Map.of(CURRENCY_ID, OFFER_CURRENCY_ID, 
                                 CURRENCY_CODE, OFFER_CURRENCY_CODE,
                                 CURRENCY_SYMBOL, OFFER_CURRENCY_SYMBOL)))
                 .publication(resultSet.getObject(OFFER_PUBLICATION_DATE, LocalDate.class))
                 .proposition(fileMeatadataMapper.
                         mapFileMetadata(resultSet, 
                                 Map.of(FILE_ID, OFFER_FILE_ID,
                                        FILE_NAME, OFFER_FILE_NAME,
                                        FILE_CONTENT_TYPE, OFFER_FILE_CONTENT_TYPE,
                                        FILE_AWS3_KEY, OFFER_FILE_AWS3_KEY)))
               .build();
    }
    
}