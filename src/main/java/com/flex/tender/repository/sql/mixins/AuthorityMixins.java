package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorityMixins {

    public final String AUTHORITY_QUERY_COLUMNS = """
            authority.id AS authority_id, 
            authority.title AS authority_title
            """;
    
}