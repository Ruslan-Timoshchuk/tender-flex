package com.flex.tender.repository.impl;

import static java.util.stream.Collectors.toSet;
import static java.lang.String.format;
import static com.flex.tender.repository.sql.query.OfferQueries.*;
import static java.util.Optional.ofNullable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.AwardDecision;
import com.flex.tender.model.Offer;
import com.flex.tender.model.RejectDecision;
import com.flex.tender.model.enumeration.EOfferStatus;
import com.flex.tender.repository.OfferRepository;
import com.flex.tender.repository.extractor.OfferCountExtractor;
import com.flex.tender.repository.extractor.TenderOfferMapExtractor;
import com.flex.tender.repository.mapper.OfferMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferRepositoryImpl.class);

    public static final String EXECUTING_SQL_QUERY_LOG = "Executing SQL Query: {}";
    public static final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.id = ?";
    public static final String SELECT_PAGE_BY_BIDDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE bidder_id = ? LIMIT ? OFFSET ?";
    public static final String SELECT_PAGE_BY_CONTRACTOR_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String SELECT_PAGE_BY_TENDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.tender_id = ? LIMIT ? OFFSET ?";
    public static final String FIND_BY_TENDER_ID_AND_GLOBAL_STATUS_IN_PATTERN_QUERY = """
            SELECT %s FROM offers offer %s 
            WHERE offer.tender_id = :tenderId AND global_status IN (:statuses)""";

    public static final String OFFER_COLUMNS_SQL_PART_QUERY = """
            offer.id AS offer_id, offer.tender_id, offer.global_status, offer.bid_price, offer.publication_date,
            offer.company_profile_id, company_profile.official_name, company_profile.registration_number,
            company_profile.country_id, country.name, country.iso_code, country.phone_code, company_profile.city,
            company_profile.contact_first_name, company_profile.contact_last_name, company_profile.contact_phone_number,
            offer.currency_id, currency.code, currency.symbol, proposition_file.id AS proposition_file_id,
            proposition_file.name AS proposition_file_name, proposition_file.content_type AS proposition_file_content_type,
            proposition_file.aws_s3_file_key AS proposition_file_aws_s3_file_key,
            offer.award_decision_id AS award_id, offer.reject_decision_id AS reject_id""";
    public static final String OFFER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN company_profiles company_profile ON company_profile.id = offer.company_profile_id
            LEFT JOIN countries country ON country.id = company_profile.country_id
            LEFT JOIN currencies currency ON currency.id = offer.currency_id
            LEFT JOIN files proposition_file ON proposition_file.id = offer.proposition_file_id
            LEFT JOIN tenders tender ON tender.id = offer.tender_id""";

    public static final String ADD_NEW_OFFER_QUERY = """
            INSERT INTO offers(bidder_id, tender_id, company_profile_id, global_status,
                               bid_price, currency_id, publication_date, proposition_file_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String UPDATE_OFFER_QUERY = """
            UPDATE offers
            SET global_status = ?, award_decision_id = ?, reject_decision_id = ?
            WHERE id = ?""";

    private final NamedParameterJdbcTemplate jdbc;
    private final OfferMapper offerMapper;
    private final OfferCountExtractor offerCountExtractor;
    private final TenderOfferMapExtractor tenderOfferMapExtractor;

    @Override
    public Offer save(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.getJdbcTemplate().update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QUERY, new String[] { "id" });
            statement.setInt(1, offer.getBidder().getId());
            statement.setInt(2, offer.getTender().getId());
            statement.setInt(3, offer.getCompanyProfile().getId());
            statement.setString(4, offer.getGlobalStatus().name());
            statement.setLong(5, offer.getBidPrice());
            statement.setInt(6, offer.getCurrency().getId());
            statement.setObject(7, offer.getPublication());
            statement.setInt(8, offer.getProposition().getId());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public void update(Offer offer) {
        Integer awardDecisionId = ofNullable(offer.getAwardDecision()).map(AwardDecision::getId).orElse(null);
        Integer rejectDecisionId = ofNullable(offer.getRejectDecision()).map(RejectDecision::getId).orElse(null);
        jdbc.getJdbcTemplate().update(UPDATE_OFFER_QUERY, offer.getGlobalStatus().name(), awardDecisionId,
                rejectDecisionId, offer.getId());
    }

    @Override
    public Set<Offer> findByBidderWithPagination(Integer bidderId, Integer limit, Integer offset) {
        String sqlQuery = format(SELECT_PAGE_BY_BIDDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.getJdbcTemplate().query(sqlQuery, offerMapper, bidderId, limit, offset).stream()
                .collect(toSet());
    }

    @Override
    public Set<Offer> findByContractorWithPagination(Integer contractorId, Integer limit,
            Integer offset) {
        String sqlQuery = format(SELECT_PAGE_BY_CONTRACTOR_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.getJdbcTemplate().query(sqlQuery, offerMapper, contractorId, limit, offset).stream()
                .collect(toSet());
    }

    @Override
    public Set<Offer> findByTenderWithPagination(Integer tenderId, Integer limit, Integer offset) {
        String sqlQuery = format(SELECT_PAGE_BY_TENDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.getJdbcTemplate().query(sqlQuery, offerMapper, tenderId, limit, offset).stream()
                .collect(toSet());
    }

    @Override
    public Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer bidderId, List<Integer> tenderIds) {
        String sqlQuery = format(FIND_BY_BIDDER_ID_AND_TENDER_ID_IN_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.query(sqlQuery, Map.of("bidderId", bidderId, "tenderIds", tenderIds), tenderOfferMapExtractor);
    }
    
    @Override
    public List<Offer> findByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        String sqlQuery = format(FIND_BY_TENDER_ID_AND_GLOBAL_STATUS_IN_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.getJdbcTemplate().query(sqlQuery, offerMapper, tenderId);
    }
    
    @Override
    public boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        return Boolean.TRUE.equals(jdbc.queryForObject(EXISTS_BY_TENDER_ID_AND_GLOBAL_STATUS_IN,
                Map.of("tenderId", tenderId, "statuses", statuses), Boolean.class));
    }
    
    @Override
    public Integer countByBidder(Integer bidderId) {
        return jdbc.getJdbcTemplate().queryForObject(COUNT_OFFERS_BY_BIDDER_QUERY, Integer.class, bidderId);
    }

    @Override
    public Integer countByContractor(Integer contractorId) {
        return jdbc.getJdbcTemplate().queryForObject(COUNT_OFFERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }

    @Override
    public Integer countAllByTender(Integer tenderId) {
        return jdbc.getJdbcTemplate().queryForObject(COUNT_OFFERS_BY_TENDER_QUERY, Integer.class, tenderId);
    }
    
    @Override
    public Map<Integer, Integer> countByTenderIdIn(List<Integer> tenderIds) {
        return jdbc.query(COUNT_OFFERS_BY_TENDER_ID_IN_QUERY, Map.of("tenderIds", tenderIds), offerCountExtractor);
    }
    
    @Override
    public Offer findById(Integer offerId) {
        String sqlQuery = format(SELECT_BY_ID_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbc.getJdbcTemplate().queryForObject(sqlQuery, offerMapper, offerId);
    }

}