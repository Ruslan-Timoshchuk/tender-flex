package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMixins {

    public final String USER_QUERY_COLUMNS = """
             usr.id AS user_id, 
             usr.first_name, 
             usr.last_name, 
             usr.email, 
             usr.password
             """;
    
}