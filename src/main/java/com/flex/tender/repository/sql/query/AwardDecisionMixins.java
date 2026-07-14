package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AwardDecisionMixins {

    public final String AWARD_DECISION_INSERT_COLUMNS = """
            tender_id, 
            award_file_id
            """;
    public final String AWARD_DECISION_INSERT_VALUE_PARAMETERS = """
            :tenderId, 
            :awardFileId
            """;
    public final String AWARD_DECISION_QUERY_COLUMNS = "award_decision.id AS award_decision_id";
    
}