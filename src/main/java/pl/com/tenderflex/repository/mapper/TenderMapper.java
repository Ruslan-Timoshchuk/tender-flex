package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.CountryMapper.*;
import static pl.com.tenderflex.repository.mapper.CompanyProfileMapper.*;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.Procedure;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.ELanguage;
import pl.com.tenderflex.model.enums.EProcedure;
import pl.com.tenderflex.model.enums.ETenderStatus;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TenderMapper implements RowMapper<Tender> {
    
    // Tender
    public static final String TENDER_ID = "tender_id";
    public static final String TENDER_DESCRIPTION = "description";
    public static final String TENDER_GLOBAL_STATUS = "global_status";
    public static final String TENDER_PUBLICATION_DATE = "publication_date";
    public static final String OFFER_SUBMISSION_DEADLINE = "offer_submission_deadline";
    public static final String TENDER_PROCEDURE_TYPE = "procedure_type";
    public static final String TENDER_PROCEDURE_LANGUAGE = "language";
     
    // Contractor country of Company Profile
    public static final String CONTRACTOR_COUNTRIES_TABLE_NAME = "contractor_country";
    public static final String CONTRACTOR_PROFILE_COUNTRY_ID = "contractor_country_id";
    public static final String CONTRACTOR_PROFILE_COUNTRY_NAME = "contractor_country_name";
    public static final String CONTRACTOR_PROFILE_COUNTRY_ISO_CODE = "contractor_country_iso_code";
    public static final String CONTRACTOR_PROFILE_COUNTRY_PHONE_CODE = "contractor_country_phone_code";
    
    // Contractor Company Profile
    public static final String CONTRACTOR_PROFILE_ID = "company_profile_id";
    public static final String CONTRACTOR_PROFILE_OFFICIAL_NAME = "contractor_official_name";
    public static final String CONTRACTOR_PROFILE_REGISTRATION_NUMBER = "contractor_registration_number";
    public static final String CONTRACTOR_PROFILE_CITY = "contractor_city";
        
    // Contractor Contact Person
    public static final String CONTRACTOR_CONTACT_FIRST_NAME = "contractor_contact_first_name";
    public static final String CONTRACTOR_CONTACT_LAST_NAME = "contractor_contact_last_name";
    public static final String CONTRACTOR_CONTACT_PHONE_NUMBER = "contractor_contact_phone_number";

    private final CountryMapper countryMapper;
    private final CompanyProfileMapper companyProfileMapper;
    private final CpvMapper cpvMapper;
    private final ContractMapper contractMapper;
    private final AwardDecisionMapper awardMapper;
    private final RejectDecisionMapper rejectMapper;
  
    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tender tender = mapTender(resultSet);
        tender.setAwardDecision(awardMapper.mapAward(resultSet));
        tender.setRejectDecision(rejectMapper.mapReject(resultSet));
        return tender;
    }
    
    public Tender mapTender(ResultSet resultSet) throws SQLException {
        CompanyProfile companyProfile = companyProfileMapper
                .mapCompanyProfile(countryMapper.mapCountry(
                        resultSet, Map.of(COUNTRY_ID, CONTRACTOR_PROFILE_COUNTRY_ID, 
                                          COUNTRY_NAME, CONTRACTOR_PROFILE_COUNTRY_NAME,
                                          COUNTRY_ISO_CODE, CONTRACTOR_PROFILE_COUNTRY_ISO_CODE, 
                                          COUNTRY_PHONE_CODE, CONTRACTOR_PROFILE_COUNTRY_PHONE_CODE)), 
                        resultSet, Map.of(PROFILE_ID, CONTRACTOR_PROFILE_ID, 
                                          PROFILE_OFFICIAL_NAME, CONTRACTOR_PROFILE_OFFICIAL_NAME,
                                          PROFILE_REGISTRATION_NUMBER, CONTRACTOR_PROFILE_REGISTRATION_NUMBER, 
                                          PROFILE_CITY, CONTRACTOR_PROFILE_CITY, 
                                          CONTACT_FIRST_NAME, CONTRACTOR_CONTACT_FIRST_NAME, 
                                          CONTACT_LAST_NAME, CONTRACTOR_CONTACT_LAST_NAME, 
                                          CONTACT_PHONE_NUMBER, CONTRACTOR_CONTACT_PHONE_NUMBER
                             )); 
        return Tender
                .builder()
                .id(resultSet.getInt(TENDER_ID))
                .companyProfile(companyProfile)
                .procedure(mapProcedure(resultSet))
                .cpv(cpvMapper.mapCpv(resultSet))
                .description(resultSet.getString(TENDER_DESCRIPTION))
                .globalStatus(ETenderStatus.valueOf(resultSet.getString(TENDER_GLOBAL_STATUS)))
                .contract(contractMapper.mapContract(resultSet))
                .publicationDate(resultSet.getObject(TENDER_PUBLICATION_DATE, LocalDate.class))
                .offerSubmissionDeadline(resultSet.getObject(OFFER_SUBMISSION_DEADLINE, LocalDate.class))
                .build();
    }
    
    private Procedure mapProcedure(ResultSet resultSet) throws SQLException {
        return Procedure
                 .builder()
                 .type(EProcedure.valueOf(resultSet.getString(TENDER_PROCEDURE_TYPE)))
                 .language(ELanguage.valueOf(resultSet.getString(TENDER_PROCEDURE_LANGUAGE)))
                 .build();
    }
  
}