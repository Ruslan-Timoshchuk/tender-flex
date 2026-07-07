package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.query.CompanyProfileMixins.*;
import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.repository.CompanyProfileRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyProfileRepositoryImpl implements CompanyProfileRepository {
    
    private final JdbcTemplate jdbcTemplate;
   
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_QUERY,
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