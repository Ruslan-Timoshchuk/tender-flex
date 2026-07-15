package com.flex.tender.repository.sql.mixins;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RejectDecisionMixins {

    public final String REJECT_DECISION_INSERT_COLUMNS = """
            tender_id, 
            reject_file_id
            """;
    public final String REJECT_DECISION_INSERT_VALUE_PARAMETERS = """
            :tenderId, 
            :rejectFileId
            """;
    public final String REJECT_DECISION_QUERY_COLUMNS = "reject_decision.id AS reject_decision_id";
    
}