package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.ContactPerson;

@Component
@RequiredArgsConstructor
public class CompanyProfileMapper {
      
    public static final String COMPANY_ID = "company_profile_id";
    public static final String OFFICIAL_NAME = "official_name";
    public static final String REGISTRATION_NUMBER = "registration_number";
    public static final String COMPANY_CITY = "city";
    public static final String CONTACT_FIRST_NAME = "contact_first_name";
    public static final String CONTACT_LAST_NAME = "contact_last_name";
    public static final String CONTACT_PHONE_NUMBER = "contact_phone_number";
    
    private final CountryMapper countryMapper;
    
    public CompanyProfile mapCompanyProfile(ResultSet resultSet) throws SQLException {    
        return CompanyProfile
                 .builder()
                 .id(resultSet.getInt(COMPANY_ID))
                 .officialName(resultSet.getString(OFFICIAL_NAME))
                 .registrationNumber(resultSet.getString(REGISTRATION_NUMBER))
                 .country(countryMapper.mapCountry(resultSet))
                 .city(resultSet.getString(COMPANY_CITY))
                 .contactPerson(mapContactPerson(resultSet))
                 .build();
    }
    
    private ContactPerson mapContactPerson(ResultSet resultSet) throws SQLException {
        return ContactPerson
                 .builder()
                 .firstName(resultSet.getString(CONTACT_FIRST_NAME))
                 .lastName(resultSet.getString(CONTACT_LAST_NAME))
                 .phoneNumber(resultSet.getString(CONTACT_PHONE_NUMBER))
                 .build();
    }
    
}