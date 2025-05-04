package pl.com.tenderflex.repository.impl;

import static java.lang.String.*;
import java.sql.PreparedStatement;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
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
    public static final String UPDATE_TENDER_QUERY = """
            UPDATE tenders SET procedure_type = ?, language = ?, cpv_id = ?, description = ?, global_status = ?
            WHERE id = ?""";
    public static final String COUNT_TENDERS_QUERY = "SELECT count(*) FROM tenders";
    public static final String COUNT_TENDERS_BY_CONTRACTOR_QUERY = "SELECT count(*) FROM tenders WHERE contractor_id = ?";
    public static final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE tender.id = ?";
    public static final String SELECT_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s LIMIT ? OFFSET ?";
    public static final String SELECT_CONTRACTOR_PAGE_PATTERN_QUERY = "SELECT %s FROM tenders tender %s WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String TENDER_COLUMNS_SQL_PART_QUERY = """
            tender.id, tender.language, tender.procedure_type, tender.description, tender.global_status, tender.publication_date,
            tender.offer_submission_deadline, tender.company_profile_id, company_profile.official_name,
            company_profile.registration_number, company_profile.country_id, country.name, country.iso_code, country.phone_code,
            company_profile.city, company_profile.contact_first_name, company_profile.contact_last_name,
            company_profile.contact_phone_number, tender.cpv_id, cpv.code, cpv.summary, contract.id AS contract_id,
            award.id AS award_id, award_file.id AS award_file_id, award_file.name AS award_file_name,
            award_file.content_type AS award_file_content_type, award_file.aws_s3_file_key AS award_aws_s3_file_key,
            reject.id AS reject_id, reject_file.id AS reject_file_id, reject_file.name AS reject_file_name,
            reject_file.content_type AS reject_file_content_type, reject_file.aws_s3_file_key AS reject_aws_s3_file_key""";
    public static final String TENDER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN cpvs cpv ON cpv.id = tender.cpv_id
            LEFT JOIN company_profiles company_profile ON company_profile.id = tender.company_profile_id
            LEFT JOIN countries country ON country.id = company_profile.country_id
            LEFT JOIN contracts contract ON contract.tender_id = tender.id
            LEFT JOIN awards award ON award.tender_id = tender.id
            LEFT JOIN files award_file ON award_file.id = award.award_file_id
            LEFT JOIN rejects reject ON reject.tender_id = tender.id
            LEFT JOIN files reject_file ON reject_file.id = reject.reject_file_id""";

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
        return tender;
    }

    @Override
    public void update(Tender tender) {
        jdbcTemplate.update(UPDATE_TENDER_QUERY, tender.getProcedure().getType().name(), tender.getProcedure().getLanguage().name(),
                tender.getCpv().getId(), tender.getDescription(), tender.getGlobalStatus().name(), tender.getId());
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