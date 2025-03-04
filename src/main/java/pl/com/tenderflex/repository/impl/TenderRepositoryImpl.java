package pl.com.tenderflex.repository.impl;

import static  java.lang.String.*;
import java.sql.PreparedStatement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.AwardDecision;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.RejectDecision;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.repository.TenderRepository;
import pl.com.tenderflex.repository.mapper.TenderMapper;

@Repository
@RequiredArgsConstructor
public class TenderRepositoryImpl implements TenderRepository {
   
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderRepositoryImpl.class);
    
    public static final String EXECUTING_SQL_QUERY_LOG = "Executing SQL Query: {}";
    public static final String ADD_NEW_TENDER_QUERY = """
            INSERT INTO tenders(contractor_id, company_profile_id, procedure_type, language, cpv_id, description, 
                                global_status, publication_date, offer_submission_deadline) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String COUNT_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public static final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public static final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE tender.id = ?";
    public static final String SELECT_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s LIMIT ? OFFSET ?";
    public static final String SELECT_CONTRACTOR_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String TENDER_COLUMNS_SQL_PART_QUERY = """
            tender.id AS tender_id, tender.language, tender.procedure_type, tender.description, 
            tender.global_status, tender.publication_date, tender.offer_submission_deadline,     
            contractor_profile.id AS contractor_profile_id, contractor_profile.official_name AS contractor_official_name,
            contractor_profile.registration_number AS contractor_registration_number,
            contractor_country.id AS contractor_country_id, contractor_country.name AS contractor_country_name,
            contractor_country.iso_code AS contractor_country_iso_code, contractor_country.phone_code AS contractor_country_phone_code,
            contractor_profile.city AS contractor_city, contractor_profile.contact_first_name AS contractor_contact_first_name,
            contractor_profile.contact_last_name AS contractor_contact_last_name, contractor_profile.contact_phone_number AS contractor_contact_phone_number,
            tender.cpv_id, cpv.code AS cpv_code, cpv.summary AS cpv_summary, 
            contract.id AS contract_id, contract_type.id AS contract_type_id, contract_type.title AS contract_type_name, contract.min_price,
            contract.max_price, currency.id AS contract_currency_id, currency.code AS contract_currency_code, currency.symbol AS contract_currency_symbol, 
            contract_file.id AS contract_file_id, contract_file.name AS contract_file_name, contract_file.content_type AS contract_file_content_type,
            contract_file.aws_s3_file_key AS contract_aws_s3_file_key, contract.signed_deadline, contract.signed_date, 
            award.id AS award_id, award_file.id AS award_file_id, award_file.name AS award_file_name, 
            award_file.content_type AS award_file_content_type, award_file.aws_s3_file_key AS award_aws_s3_file_key,
            reject.id AS reject_id, reject_file.id AS reject_file_id, reject_file.name AS reject_file_name,
            reject_file.content_type AS reject_file_content_type, reject_file.aws_s3_file_key AS reject_aws_s3_file_key""";
    public static final String TENDER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN cpvs cpv ON cpv.id = tender.cpv_id
            LEFT JOIN company_profiles contractor_profile ON contractor_profile.id = tender.company_profile_id
            LEFT JOIN countries contractor_country ON contractor_country.id = contractor_profile.country_id
            LEFT JOIN contracts contract ON contract.tender_id = tender.id
            LEFT JOIN contract_types contract_type ON contract_type.id = contract.contract_type_id
            LEFT JOIN currencies currency ON currency.id = contract.currency_id
            LEFT JOIN files contract_file ON contract_file.id = contract.file_id
            LEFT JOIN awards award ON award.tender_id = tender.id
            LEFT JOIN files award_file ON award_file.id = award.award_file_id
            LEFT JOIN rejects reject ON reject.tender_id = tender.id
            LEFT JOIN files reject_file ON reject_file.id = reject.reject_file_id""";
           
    public static final String UPDATE_TENDER_QUERY = "";
    
    private final JdbcTemplate jdbcTemplate;
    private final TenderMapper tenderMapper;

    @Override
    public Tender save(Tender tender) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_QUERY, new String[] { "id" });
            statement.setInt(1, tender.getContractorId());
            statement.setInt(2, tender.getCompanyProfile().getId());
            statement.setString(3, tender.getProcedure().getType().name());
            statement.setString(4, tender.getProcedure().getLanguage().name());
            statement.setInt(5, tender.getCpv().getId());
            statement.setString(6, tender.getDescription());      
            statement.setString(7, tender.getGlobalStatus().name());
            statement.setObject(8, tender.getPublicationDate());
            statement.setObject(9, tender.getOfferSubmissionDeadline());
            return statement;
        }, keyHolder);
        tender.setId(keyHolder.getKeyAs(Integer.class));
        Contract contract = tender.getContract();
        contract.setTender(tender);
        AwardDecision award = tender.getAwardDecision();
        award.setTender(tender);
        RejectDecision reject = tender.getRejectDecision();
        reject.setTender(tender);
        return tender;
    }
    
    @Override
    public List<Tender> findWithPagination(Integer amountTenders, Integer amountTendersToSkip) {
        String sqlQuery = format(SELECT_PAGE_PATTERN_QUERY, TENDER_COLUMNS_SQL_PART_QUERY,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, tenderMapper, amountTenders, amountTendersToSkip);
    }

    @Override
    public List<Tender> findByContractorWithPagination(Integer contractorId, Integer amountTenders,
            Integer amountTendersToSkip) {
        String sqlQuery = format(SELECT_CONTRACTOR_PAGE_PATTERN_QUERY, TENDER_COLUMNS_SQL_PART_QUERY,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, tenderMapper, contractorId, amountTenders, amountTendersToSkip);
    }

    @Override
    public Integer countTendersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }

    @Override
    public Integer countTenders() {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_QUERY, Integer.class);
    }

    @Override
    public Tender findById(Integer tenderId) {
        String sqlQuery = format(SELECT_BY_ID_PATTERN_QUERY, TENDER_COLUMNS_SQL_PART_QUERY,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.queryForObject(sqlQuery, tenderMapper, tenderId);
    }
    
}