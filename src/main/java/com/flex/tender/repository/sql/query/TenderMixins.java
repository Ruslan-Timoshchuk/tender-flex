package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TenderMixins {

    public final String ADD_NEW_TENDER_QUERY = """
            INSERT INTO tenders(contractor_id, company_profile_id, procedure_type, language, cpv_id, description,
                                global_status, publication_date, offer_submission_deadline)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    public final String UPDATE_TENDER_QUERY = """
            UPDATE tenders SET procedure_type = ?, language = ?, cpv_id = ?, description = ?, global_status = ?
            WHERE id = ?""";
    public final String COUNT_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public final String FIND_BY_ID_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE tender.id = ?";
    public final String SELECT_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s LIMIT ? OFFSET ?";
    public final String SELECT_CONTRACTOR_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public final String SELECT_ACTIVE_WITH_EXPIRED_SUBMISSION_PATTERN_QUERY = """
            SELECT %s FROM tenders tender %s
            WHERE tender.global_status = ? AND offer_submission_deadline <= ?""";  
    public final String TENDER_COLUMNS_SQL = """
            tender.id AS tender_id, tender.language, tender.procedure_type, tender.description, tender.global_status, tender.publication_date,
            tender.offer_submission_deadline, tender.company_profile_id, company_profile.official_name,
            company_profile.registration_number, company_profile.country_id, country.name, country.iso_code, country.phone_code,
            company_profile.city, company_profile.contact_first_name, company_profile.contact_last_name,
            company_profile.contact_phone_number, tender.cpv_id, cpv.code, cpv.summary""";
    public final String TENDER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN cpvs cpv ON cpv.id = tender.cpv_id
            LEFT JOIN company_profiles company_profile ON company_profile.id = tender.company_profile_id
            LEFT JOIN countries country ON country.id = company_profile.country_id""";
   
   
    
}