package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TenderMixins {

    public final String TENDER_INSERT_COLUMNS = """
            contractor_id, 
            company_profile_id, 
            procedure_type, 
            language, 
            cpv_id, 
            description, 
            global_status, 
            publication_date, 
            offer_submission_deadline""";
    public final String TENDER_INSERT_VALUE_PARAMETERS = """
            :userId,
            :companyProfileId,
            :procedureType,
            :language,
            :cpvId,
            :description,
            :globalStatus,
            :publicationDate,
            :offerSubmissionDeadline
            """;
    public final String TENDER_UPDATE_SET_CLAUSE = """
            procedure_type = :procedureType,
            language = :language,
            cpv_id = :cpvId,
            description = :description,
            global_status = :globalStatus
            """;
    public final String TENDER_QUERY_COLUMNS = """
            tender.id AS tender_id, 
            tender.language, 
            tender.procedure_type, 
            tender.description, 
            tender.global_status, 
            tender.publication_date,
            tender.offer_submission_deadline
            """;
}