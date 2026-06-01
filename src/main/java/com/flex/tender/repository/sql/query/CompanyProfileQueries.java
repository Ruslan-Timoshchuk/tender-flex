package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CompanyProfileQueries {

    public final String ADD_NEW_QUERY = """
            INSERT INTO company_profiles(official_name, registration_number, country_id, city, 
                                         contact_first_name, contact_last_name, contact_phone_number)
            VALUES (?, ?, ?, ?, ?, ?, ?)""";
    
}