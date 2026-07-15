package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorityMixins {

    public final String AUTHORITY_QUERY_COLUMNS = """
            authority.id AS authority_id, 
            authority.title AS authority_title
            """;
    public final String USER_AUTHORITY_JOIN_USERS = """
            users_authorities user_authority ON 
            user_authority.user_id = usr.id
            """;
    public final String AUTHORITY_JOIN_USERS_AUTHORITIES = """
            authorities authority ON 
            authority.id = user_authority.authority_id
            """;
}