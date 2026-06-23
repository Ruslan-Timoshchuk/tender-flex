package com.flex.tender.repository.impl;

import static java.lang.String.*;
import static java.util.stream.Collectors.toSet;
import static com.flex.tender.repository.sql.query.TenderMixins.*;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Tender;
import com.flex.tender.model.enumeration.ETenderStatus;
import com.flex.tender.repository.TenderRepository;
import com.flex.tender.repository.extractor.OfferTenderMapExtractor;
import com.flex.tender.repository.mapper.TenderMapper;
import com.flex.tender.repository.sql.query.CompanyProfileMixins;
import com.flex.tender.repository.sql.query.CountryMixins;
import com.flex.tender.repository.sql.query.CpvMixins;
import com.flex.tender.repository.sql.query.OfferMixins;
import com.flex.tender.repository.sql.query.TenderMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TenderRepositoryImpl implements TenderRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderRepositoryImpl.class);

    public static final String EXECUTING_SQL_QUERY_LOG = "Executing SQL Query: {}";
    public static final String FIND_BY_OFFER_ID_IN_PATTERN_QUERY ="""
            SELECT %s, %s 
            FROM tenders tender 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s     
            WHERE offer.id IN (:offerIds)
            """.formatted(
                OfferMixins.OFFER_ID_COLUMN_SQL,
                TenderMixins.TENDER_COLUMNS_SQL, 
                CpvMixins.CPV_JOIN_TENDERS_SQL,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS_SQL,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES_SQL,
                OfferMixins.OFFER_JOIN_TENDERS_SQL);
    
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate jdbc;
    private final TenderMapper tenderMapper;
    private final OfferTenderMapExtractor offerTenderMapExtractor;

    @Override
    public Tender save(Tender tender) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_TENDER_QUERY, new String[] { "id" });
            statement.setInt(1, tender.getContractor().getId());
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
        jdbcTemplate.update(UPDATE_TENDER_QUERY, tender.getProcedure().getType().name(),
                tender.getProcedure().getLanguage().name(), tender.getCpv().getId(), tender.getDescription(),
                tender.getGlobalStatus().name(), tender.getId());
    }

    @Override
    public Set<Tender> findWithPagination(Integer amountTenders, Integer amountTendersToSkip) {
        String sqlQuery = format(SELECT_PAGE_PATTERN_QUERY, TENDER_COLUMNS_SQL,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, tenderMapper, amountTenders, amountTendersToSkip).stream().collect(toSet());
    }

    @Override
    public Set<Tender> findByContractorWithPagination(Integer contractorId, Integer amountTenders,
            Integer amountTendersToSkip) {
        String sqlQuery = format(SELECT_CONTRACTOR_PAGE_PATTERN_QUERY, TENDER_COLUMNS_SQL,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, tenderMapper, contractorId, amountTenders, amountTendersToSkip).stream()
                .collect(toSet());
    }

    @Override
    public Integer countByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }

    @Override
    public Integer countAll() {
        return jdbcTemplate.queryForObject(COUNT_TENDERS_QUERY, Integer.class);
    }

    @Override
    public Tender findById(Integer tenderId) {
        String sqlQuery = format(FIND_BY_ID_PATTERN_QUERY, TENDER_COLUMNS_SQL,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.queryForObject(sqlQuery, tenderMapper, tenderId);
    }

    @Override
    public Set<Tender> findActiveWhereSubmissionIsExpired(ETenderStatus status, LocalDate currentDate) {
        String sqlQuery = format(SELECT_ACTIVE_WITH_EXPIRED_SUBMISSION_PATTERN_QUERY, TENDER_COLUMNS_SQL,
                TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate
                .query(sqlQuery, tenderMapper, status.name(), currentDate).stream()
                .collect(toSet());
    }

    @Override
    public Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds) {
        return jdbc.query(FIND_BY_OFFER_ID_IN_PATTERN_QUERY, Map.of("offerIds", offerIds), offerTenderMapExtractor);
    }

}