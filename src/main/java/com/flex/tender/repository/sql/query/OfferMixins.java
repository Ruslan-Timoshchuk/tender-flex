package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OfferMixins {

    public final String OFFER_INSER_COLUMNS = """
            bidder_id, 
            tender_id, 
            company_profile_id, 
            global_status,          
            bid_price, 
            currency_id, 
            publication_date, 
            proposition_file_id
            """;
    public final String OFFER_INSER_VALUE_PARAMETERS = """
            :userId, 
            :tenderId, 
            :companyProfileId, 
            :globalStatus,          
            :bidPrice, 
            :currencyId, 
            :publicationDate, 
            :propositionFileId
            """;
    public final String OFFER_UPDATE_SET_CLAUSE = """
            global_status = :globalStatus, 
            award_decision_id = :awardDecisionId, 
            reject_decision_id = :rejectDecisionId
            """;
    public static final String OFFER_QUERY_COLUMNS = """
            offer.id AS offer_id, 
            offer.global_status, 
            offer.bid_price, 
            offer.publication_date
            """;
    public final String OFFER_ID_QUERY_COLUMN = "offer.id AS offer_id";
    public final String OFFER_JOIN_TENDERS = "offers offer ON offer.tender_id = tender.id";
    
}