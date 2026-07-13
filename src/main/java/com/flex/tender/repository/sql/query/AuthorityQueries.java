package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorityQueries {

    public final String FIND_ALL_QUERY = "SELECT id, title FROM authorities";
    
}