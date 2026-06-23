package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CpvMixins {

    public final String CPV_JOIN_TENDERS_SQL = "cpvs cpv ON cpv.id = tender.cpv_id";
    
    public final String FIND_ALL_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs";
    public final String FIND_BY_ID_QUERY = "SELECT id AS cpv_id, code, summary FROM cpvs WHERE id = :id";
    public final String FIND_BY_OFFER_ID_IN_QUERY = """
            SELECT c.id AS cpv_id, code, summary, o.id AS offer_id 
            FROM offers o
            JOIN tenders t ON o.tender_id = t.id
            JOIN cpvs c ON t.cpv_id = c.id
            WHERE o.id IN (:offerIds)""";  
    public final String FIND_BY_TENDER_ID_IN_QUERY = """
            SELECT tr.id AS tender_id, cv.id AS cpv_id, cv.code, cv.summary 
            FROM cpvs cv 
            LEFT JOIN tenders tr on cv.id = tr.cpv_id 
            WHERE tr.id IN (:tenderIds)""";    
    
}