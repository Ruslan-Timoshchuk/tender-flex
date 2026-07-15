package com.flex.tender.repository.mapper;

import static com.flex.tender.repository.sql.column.CompanyProfileColumns.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.stereotype.Component;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.model.embedded.ContactPerson;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CompanyProfileMapper {
      
    private final CountryMapper countryMapper;
    
    public CompanyProfile mapCompanyProfile(ResultSet resultSet) throws SQLException {    
        return CompanyProfile.builder()
                             .id(resultSet.getInt(COMPANY_ID))
                             .officialName(resultSet.getString(OFFICIAL_NAME))
                             .registrationNumber(resultSet.getString(REGISTRATION_NUMBER))
                             .country(countryMapper.mapCountry(resultSet))
                             .city(resultSet.getString(COMPANY_CITY))
                             .contactPerson(mapContactPerson(resultSet))
               .build();
    }
    
    private ContactPerson mapContactPerson(ResultSet resultSet) throws SQLException {
        return ContactPerson.builder()
                            .firstName(resultSet.getString(CONTACT_FIRST_NAME))
                            .lastName(resultSet.getString(CONTACT_LAST_NAME))
                            .phoneNumber(resultSet.getString(CONTACT_PHONE_NUMBER))
               .build();
    }
    
}