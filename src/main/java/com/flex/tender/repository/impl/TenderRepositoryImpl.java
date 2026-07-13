package com.flex.tender.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    public static final String INSERT_QUERY = """
            INSERT INTO tenders(%s)
            VALUES (%s)
            """.formatted(
                TenderMixins.TENDER_INSERT_COLUMNS,
                TenderMixins.TENDER_INSERT_VALUE_PARAMETERS);
    public static final String UPDATE_QUERY = """
            UPDATE tenders 
            SET %s
            WHERE id = :id
            """.formatted(
                TenderMixins.TENDER_UPDATE_SET_CLAUSE);
    public static final String FIND_PAGE_QUERY = """
            SELECT %s, %s, %s, %s 
            FROM tenders tender 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LIMIT :limit OFFSET :offset
            """.formatted(
                TenderMixins.TENDER_QUERY_COLUMNS, 
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CpvMixins.CPV_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CpvMixins.CPV_JOIN_TENDERS);
    public static final String FIND_CONTRACTOR_PAGE_QUERY = """
           SELECT %s, %s, %s, %s 
           FROM tenders tender 
           LEFT JOIN %s 
           LEFT JOIN %s 
           LEFT JOIN %s 
           WHERE contractor_id = :contractorId LIMIT :limit OFFSET :offset
           """.formatted(
               TenderMixins.TENDER_QUERY_COLUMNS, 
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CpvMixins.CPV_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CpvMixins.CPV_JOIN_TENDERS);
    public static final String COUNT_ALL_QUERY = """
           SELECT count(*) 
           FROM tenders""";
    public static final String COUNT_ALL_BY_CONTRACTOR_QUERY = """
           SELECT count(*) 
           FROM tenders 
           WHERE contractor_id = :contractorId""";
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s, %s, %s 
            FROM tenders tender 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            WHERE tender.id = :tenderId
            """.formatted(
                TenderMixins.TENDER_QUERY_COLUMNS, 
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CpvMixins.CPV_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CpvMixins.CPV_JOIN_TENDERS);
    public static final String FIND_BY_OFFER_ID_IN_QUERY ="""
            SELECT %s, %s, %s, %s, %s 
            FROM tenders tender 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s      
            WHERE offer.id IN (:offerIds)
            """.formatted(
                OfferMixins.OFFER_ID_QUERY_COLUMN,
                TenderMixins.TENDER_QUERY_COLUMNS, 
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CpvMixins.CPV_QUERY_COLUMNS,
                OfferMixins.OFFER_JOIN_TENDERS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CpvMixins.CPV_JOIN_TENDERS);
    public static final String FIND_ACTIVE_WITH_EXPIRED_SUBMISSION_QUERY = """
            SELECT %s, %s, %s, %s 
            FROM tenders tender 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s
            WHERE tender.global_status = :status 
            AND offer_submission_deadline <= :date
            """.formatted(
                TenderMixins.TENDER_QUERY_COLUMNS, 
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CpvMixins.CPV_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_TENDERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CpvMixins.CPV_JOIN_TENDERS);  
    
    private final NamedParameterJdbcTemplate jdbc;
    private final TenderMapper tenderMapper;
    private final OfferTenderMapExtractor offerTenderMapExtractor;

    @Override
    public Tender save(Tender tender) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", tender.getContractor().getId())
                .addValue("companyProfileId", tender.getCompanyProfile().getId())
                .addValue("procedureType", tender.getProcedure().getType().name())
                .addValue("language", tender.getProcedure().getLanguage().name())
                .addValue("cpvId", tender.getCpv().getId())
                .addValue("description", tender.getDescription())
                .addValue("globalStatus", tender.getGlobalStatus().name())
                .addValue("publicationDate", tender.getPublicationDate())
                .addValue("offerSubmissionDeadline", tender.getOfferSubmissionDeadline());
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        tender.setId(keyHolder.getKeyAs(Integer.class));
        return tender;
    }

    @Override
    public void update(Tender tender) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", tender.getId())
                .addValue("procedureType", tender.getProcedure().getType().name())
                .addValue("language", tender.getProcedure().getLanguage().name())
                .addValue("cpvId", tender.getCpv().getId())
                .addValue("description", tender.getDescription())
                .addValue("globalStatus", tender.getGlobalStatus().name());
        jdbc.update(UPDATE_QUERY, parameters);
    }

    @Override
    public List<Tender> findWithPagination(Integer limit, Integer offset) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return jdbc.query(FIND_PAGE_QUERY, parameters, tenderMapper);
    }

    @Override
    public List<Tender> findByContractorWithPagination(Integer contractorId, Integer limit,
            Integer offset) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("contractorId", contractorId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return jdbc.query(FIND_CONTRACTOR_PAGE_QUERY, parameters, tenderMapper);
    }

    @Override
    public Integer countByContractor(Integer contractorId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("contractorId", contractorId);
        return jdbc.queryForObject(COUNT_ALL_BY_CONTRACTOR_QUERY, parameters, Integer.class);
    }

    @Override
    public Integer countAll() {
        return jdbc.queryForObject(COUNT_ALL_QUERY, new MapSqlParameterSource(), Integer.class);
    }

    @Override
    public Tender findById(Integer tenderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, tenderMapper);
    }

    @Override
    public List<Tender> findActiveWhereSubmissionIsExpired(ETenderStatus status, LocalDate date) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("status", status.name())
                .addValue("date", date);
        return jdbc.query(FIND_ACTIVE_WITH_EXPIRED_SUBMISSION_QUERY, parameters, tenderMapper);
    }

    @Override
    public Map<Integer, Tender> findByOfferIdIn(List<Integer> offerIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("offerIds", offerIds);
        return jdbc.query(FIND_BY_OFFER_ID_IN_QUERY, parameters, offerTenderMapExtractor);
    }

}