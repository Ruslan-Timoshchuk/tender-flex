package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpvQueries {

    public static final String FIND_ALL_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs";
    public static final String FIND_BY_ID_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs WHERE id = :id";
    public static final String FIND_BY_TENDER_ID_QUERY = """
             SELECT cv.id AS cpv_id, cv.code, cv.summary 
             FROM cpvs cv 
             LEFT JOIN tenders tr ON tr.cpv_id = cv.id 
             WHERE tr.id = :tenderId""";
    public static final String FIND_BY_OFFER_ID_IN_QUERY = """
            SELECT c.id AS cpv_id, code, summary, o.id AS offer_id 
            FROM offers o
            JOIN tenders t ON o.tender_id = t.id
            JOIN cpvs c ON t.cpv_id = c.id
            WHERE o.id IN (:offerIds)""";  
    public static final String FIND_BY_TENDER_ID_IN_QUERY = """
            SELECT tr.id AS tender_id, cv.id AS cpv_id, cv.code, cv.summary 
            FROM cpvs cv 
            LEFT JOIN tenders tr on cv.id = tr.cpv_id 
            WHERE tr.id IN (:tenderIds)""";    
    
}