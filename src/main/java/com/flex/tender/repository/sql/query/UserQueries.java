package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserQueries {

    public final String FIND_BY_EMAIL_QUERY = """
            SELECT ur.id AS user_id, ur.first_name, ur.last_name, ur.email, 
                   ur.password, ay.id AS authority_id, ay.title AS authority_title
            FROM users ur 
            LEFT JOIN users_authorities uray ON uray.user_id = ur.id 
            LEFT JOIN authorities ay ON ay.id = uray.authority_id 
            WHERE email = ?""";
    
    public final String FIND_BY_ID_QUERY = """
            SELECT ur.id AS user_id, ur.first_name, ur.last_name, ur.email, 
                   ur.password, ay.id AS authority_id, ay.title AS authority_title
            FROM users ur 
            LEFT JOIN users_authorities uray ON uray.user_id = ur.id 
            LEFT JOIN authorities ay ON ay.id = uray.authority_id 
            WHERE ur.id = ?""";
    
}