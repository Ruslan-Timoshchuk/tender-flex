package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OfferQueries {

    public final String COUNT_OFFERS_BY_BIDDER_QUERY = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(id) FROM offers WHERE tender_id = ?";
    public final String COUNT_OFFERS_BY_CONTRACTOR_QUERY = """
           SELECT count(o.id) 
           FROM offers o 
           LEFT JOIN tenders t ON o.tender_id = t.id 
           WHERE contractor_id = ?""";
    public final String COUNT_OFFERS_BY_TENDER_ID_IN_QUERY = """
           SELECT tender_id, COUNT(*) as offers 
           FROM offers 
           WHERE tender_id IN (:tenderIds) 
           GROUP BY tender_id""";
    public final String EXISTS_BY_TENDER_ID_AND_GLOBAL_STATUS_IN = """
           SELECT EXISTS 
           (SELECT 1 FROM offers 
           WHERE id = :tenderId AND global_status IN (:statuses))""";
    public final String FIND_BY_BIDDER_ID_AND_TENDER_ID_IN_PATTERN_QUERY = """
            SELECT %s FROM offers offer %s 
            WHERE offer.bidder_id = :bidderId AND offer.tender_id IN (:tenderIds)""";
}