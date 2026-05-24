package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CountryQueries {

    public final String FIND_ALL_QUERY = "SELECT id AS country_id, name, iso_code, phone_code FROM countries";
    public final String FIND_BY_ID_QUERY = "SELECT id AS country_id, name, iso_code, phone_code FROM countries WHERE id = ?";
    
}