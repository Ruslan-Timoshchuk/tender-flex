package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpvQueries {

    public static final String FIND_ALL_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs";
    public static final String FIND_BY_ID_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs WHERE id = :id";
    public static final String FIND_BY_OFFER_ID_IN_QUERY = """
            SELECT c.id AS cpv_id, code, summary, o.id AS offer_id 
            FROM offers o
            JOIN tenders t ON o.tender_id = t.id
            JOIN cpvs c ON t.cpv_id = c.id
            WHERE o.id IN (:offerIds)""";    
    
}