package pl.com.tenderflex.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyProfile;
import pl.com.tenderflex.repository.CompanyProfileRepository;

@Repository
@RequiredArgsConstructor
public class CompanyProfileRepositoryImpl implements CompanyProfileRepository {
      
    public static final String ADD_NEW_COMPANY_PROFILE_QUERY = """
            INSERT INTO company_profiles(official_name, registration_number, country_id, city, 
                                         contact_first_name, contact_last_name, contact_phone_number)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";
    
    private final JdbcTemplate jdbcTemplate;
   
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_COMPANY_PROFILE_QUERY,
                    new String[] { "id" });
            statement.setString(1, companyProfile.getOfficialName());
            statement.setString(2, companyProfile.getRegistrationNumber());
            statement.setInt(3, companyProfile.getCountry().getId());
            statement.setString(4, companyProfile.getCity());
            statement.setString(5, companyProfile.getContactPerson().getFirstName());
            statement.setString(6, companyProfile.getContactPerson().getLastName());
            statement.setString(7, companyProfile.getContactPerson().getPhoneNumber());
            return statement;
        }, keyHolder);
        companyProfile.setId(keyHolder.getKeyAs(Integer.class));
        return companyProfile;
    }

}