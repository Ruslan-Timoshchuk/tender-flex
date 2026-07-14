package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CompanyProfileMixins {

    public final String COMPANY_PROFILE_INSERT_COLUMNS = """
            official_name, 
            registration_number, 
            country_id, 
            city, 
            contact_first_name, 
            contact_last_name, 
            contact_phone_number
            """;
    public final String COMPANY_PROFILE_INSERT_VALUE_PARAMETERS = """
            :officialName, 
            :registrationNumber, 
            :countryId, 
            :city, 
            :contactFirstName, 
            :contactLastName, 
            :contactPhoneNumber
            """;
    public final String COMPANY_PROFILE_QUERY_COLUMNS = """
            company_profile.id AS company_profile_id, 
            company_profile.official_name,
            company_profile.registration_number,
            company_profile.city, 
            company_profile.contact_first_name, 
            company_profile.contact_last_name,
            company_profile.contact_phone_number""";
    public final String COMPANY_PROFILE_JOIN_TENDERS ="""
            company_profiles company_profile ON 
            company_profile.id = tender.company_profile_id""";
    public final String COMPANY_PROFILE_JOIN_OFFERS ="""
            company_profiles company_profile ON 
            company_profile.id = offer.company_profile_id""";
    
}