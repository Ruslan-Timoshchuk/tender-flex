package pl.com.tenderflex.companyprofile.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.model.CompanyProfile;
import pl.com.tenderflex.companyprofile.model.ContactPerson;

@Component
@RequiredArgsConstructor
public class CompanyProfileMapper implements RowMapper<CompanyProfile> {

    private final CountryMapper countryMapper;
    
    @Override
    public CompanyProfile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return mapCompanyProfile(resultSet);
    }

    public CompanyProfile mapCompanyProfile(ResultSet resultSet) throws SQLException {
        return CompanyProfile
                 .builder()
                 .id(resultSet.getInt("id"))
                 .country(countryMapper.mapCountry(resultSet))
                 .city(resultSet.getString("city"))
                 .officialName(resultSet.getString("official_name"))
                 .registrationNumber(resultSet.getString("registration_number"))
                 .contactPerson(mapContactPerson(resultSet))
                 .build();
    }
    
    private ContactPerson mapContactPerson(ResultSet resultSet) throws SQLException {
        return ContactPerson
                 .builder()
                 .firstName(resultSet.getString("first_name"))
                 .lastName(resultSet.getString("last_name"))
                 .phoneNumber(resultSet.getString("phone_number"))
                 .build();
    }

}