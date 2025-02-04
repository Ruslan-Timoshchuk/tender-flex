package pl.com.tenderflex.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Country;

@Component
@RequiredArgsConstructor
public class CompanyProfileMapper {
      
    public static final String PROFILE_ID = "id";
    public static final String PROFILE_OFFICIAL_NAME = "official_name";
    public static final String PROFILE_REGISTRATION_NUMBER = "registration_number";
    public static final String PROFILE_CITY = "city";
    public static final String CONTACT_FIRST_NAME = "contact_first_name";
    public static final String CONTACT_LAST_NAME = "contact_last_name";
    public static final String CONTACT_PHONE_NUMBER = "contact_phone_number";
    
    public CompanyProfile mapCompanyProfile(Country country, ResultSet resultSet, 
                                            Map<String, String> columnLabels) throws SQLException {    
        return CompanyProfile
                 .builder()
                 .id(resultSet.getInt(columnLabels.get(PROFILE_ID)))
                 .officialName(resultSet.getString(columnLabels.get(PROFILE_OFFICIAL_NAME)))
                 .registrationNumber(resultSet.getString(columnLabels.get(PROFILE_REGISTRATION_NUMBER)))
                 .country(country)
                 .city(resultSet.getString(columnLabels.get(PROFILE_CITY)))
                 .contactPerson(mapContactPerson(resultSet, columnLabels))
                 .build();
    }
    
    private ContactPerson mapContactPerson(ResultSet resultSet, Map<String, String> columnLabels) throws SQLException {
        return ContactPerson
                 .builder()
                 .firstName(resultSet.getString(columnLabels.get(CONTACT_FIRST_NAME)))
                 .lastName(resultSet.getString(columnLabels.get(CONTACT_LAST_NAME)))
                 .phoneNumber(resultSet.getString(columnLabels.get(CONTACT_PHONE_NUMBER)))
                 .build();
    }
    
}