package com.flex.tender.repository.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.CompanyProfile;
import com.flex.tender.repository.CompanyProfileRepository;
import com.flex.tender.repository.sql.mixins.CompanyProfileMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CompanyProfileRepositoryImpl implements CompanyProfileRepository {
    
    public static final String INSERT_QUERY = """
            INSERT INTO company_profiles(%s)
            VALUES (%s)
            """.formatted(
                CompanyProfileMixins.COMPANY_PROFILE_INSERT_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_INSERT_VALUE_PARAMETERS);
    
    private final NamedParameterJdbcTemplate jdbc;
   
    @Override
    public CompanyProfile save(CompanyProfile companyProfile) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("officialName", companyProfile.getOfficialName())
                .addValue("registrationNumber", companyProfile.getRegistrationNumber())
                .addValue("countryId", companyProfile.getCountry().getId())
                .addValue("city", companyProfile.getCity())
                .addValue("contactFirstName", companyProfile.getContactPerson().getFirstName())
                .addValue("contactLastName", companyProfile.getContactPerson().getLastName())
                .addValue("contactPhoneNumber", companyProfile.getContactPerson().getPhoneNumber());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        companyProfile.setId(keyHolder.getKeyAs(Integer.class));
        return companyProfile;
    }

}