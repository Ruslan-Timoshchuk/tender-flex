package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CountryMixins {

    public final String COUNTRY_QUERY_COLUMNS = """
            country.id AS country_id,
            country.name,
            country.iso_code,
            country.phone_code
            """;
    public final String COUNTRY_JOIN_COMPANY_PROFILES = """
            countries country ON 
            country.id = company_profile.country_id
            """;
    
}