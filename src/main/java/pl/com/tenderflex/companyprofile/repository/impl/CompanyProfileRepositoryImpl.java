package pl.com.tenderflex.companyprofile.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.companyprofile.model.CompanyProfile;
import pl.com.tenderflex.companyprofile.repository.CompanyProfileRepository;
import pl.com.tenderflex.companyprofile.repository.mapper.CompanyProfileMapper;

@Repository
@RequiredArgsConstructor
public class CompanyProfileRepositoryImpl implements CompanyProfileRepository {

    public static final String ADD_NEW_COMPANY_PROFILE_QUERY = "INSERT INTO "
            + "company_profiles(official_name, registration_number, country_id, city, first_name, last_name, phone_number) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_COMPANY_PROFILE_BY_ID_QUERY = "SELECT cp.id, cp.official_name, cp.registration_number, "
            + "cp.country_id, cs.country_name, cp.city, cp.first_name, cp.last_name, cp.phone_number "
            + "FROM company_profiles cp LEFT JOIN countries cs ON cs.id = cp.country_id WHERE cp.id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final CompanyProfileMapper companyProfileMapper;
    
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_COMPANY_PROFILE_QUERY, new String[] { "id" });
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
    
    @Override
    public CompanyProfile findById(Integer id) {
       return jdbcTemplate.queryForObject(FIND_COMPANY_PROFILE_BY_ID_QUERY, companyProfileMapper, id);
    }

}