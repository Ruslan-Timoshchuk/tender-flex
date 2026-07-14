package com.flex.tender.repository.impl;

import static java.util.Optional.ofNullable;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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
import com.flex.tender.repository.sql.query.CompanyProfileMixins;
import com.flex.tender.repository.sql.query.CountryMixins;
import com.flex.tender.repository.sql.query.CurrencyMixins;
import com.flex.tender.repository.sql.query.FileMetadataMixins;
import com.flex.tender.repository.sql.query.OfferMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    public static final String INSERT_QUERY = """
            INSERT INTO offers(%s)
            VALUES (%s)
            """.formatted(
                OfferMixins.OFFER_INSER_COLUMNS,
                OfferMixins.OFFER_INSER_VALUE_PARAMETERS);
    public static final String UPDATE_QUERY = """
            UPDATE offers
            SET %s
            WHERE id = :id
            """.formatted(
                OfferMixins.OFFER_UPDATE_SET_CLAUSE);
    public static final String FIND_BIDDER_PAGE_QUERY = """
            SELECT %s, %s, %s, %s, %s 
            FROM offers offer 
            LEFT JOIN %s
            LEFT JOIN %s 
            LEFT JOIN %s
            LEFT JOIN %s
            WHERE bidder_id = :bidderId LIMIT :limit OFFSET :offset
            """.formatted(
                OfferMixins.OFFER_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CurrencyMixins.CURRENCY_JOIN_OFFERS,
                FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);
    public static final String FIND_CONTRACTOR_PAGE_QUERY = """
           SELECT %s, %s, %s, %s, %s 
           FROM offers offer 
           LEFT JOIN %s
           LEFT JOIN %s 
           LEFT JOIN %s
           LEFT JOIN %s 
           WHERE contractor_id = :contractorId LIMIT :limit OFFSET :offset
           """.formatted(
               OfferMixins.OFFER_QUERY_COLUMNS,
               CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
               CountryMixins.COUNTRY_QUERY_COLUMNS,
               CurrencyMixins.CURRENCY_QUERY_COLUMNS,
               FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
               CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
               CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
               CurrencyMixins.CURRENCY_JOIN_OFFERS,
               FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);
    public static final String FIND_BY_TENDER_PAGE_QUERY = """
           SELECT %s, %s, %s, %s, %s 
           FROM offers offer 
           LEFT JOIN %s
           LEFT JOIN %s 
           LEFT JOIN %s
           LEFT JOIN %s
           WHERE offer.tender_id = :tenderId LIMIT :limit OFFSET :offset
           """.formatted(
               OfferMixins.OFFER_QUERY_COLUMNS,
               CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
               CountryMixins.COUNTRY_QUERY_COLUMNS,
               CurrencyMixins.CURRENCY_QUERY_COLUMNS,
               FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
               CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
               CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
               CurrencyMixins.CURRENCY_JOIN_OFFERS,
               FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);
    public static final String FIND_BY_BIDDER_ID_AND_TENDER_ID_IN_QUERY = """
            SELECT %s, %s, %s, %s, %s 
            FROM offers offer 
            LEFT JOIN %s
            LEFT JOIN %s 
            LEFT JOIN %s
            LEFT JOIN %s
            WHERE offer.bidder_id = :bidderId 
            AND offer.tender_id IN (:tenderIds)
            """.formatted(
                OfferMixins.OFFER_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CurrencyMixins.CURRENCY_JOIN_OFFERS,
                FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);
    public static final String FIND_BY_TENDER_ID_AND_GLOBAL_STATUS_IN_PATTERN_QUERY = """
            SELECT %s, %s, %s, %s, %s 
            FROM offers offer 
            LEFT JOIN %s
            LEFT JOIN %s 
            LEFT JOIN %s
            LEFT JOIN %s
            WHERE offer.tender_id = :tenderId 
            AND global_status IN (:statuses)
            """.formatted(
                OfferMixins.OFFER_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CurrencyMixins.CURRENCY_JOIN_OFFERS,
                FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);
    public static final String EXISTS_BY_TENDER_ID_AND_GLOBAL_STATUS_IN = """
            SELECT EXISTS 
            (SELECT 1 FROM offers 
            WHERE tender_id = :tenderId AND global_status IN (:statuses))""";
    public static final String COUNT_OFFERS_BY_BIDDER_QUERY = "SELECT count(id) FROM offers WHERE bidder_id = :bidderId";
    public static final String COUNT_OFFERS_BY_CONTRACTOR_QUERY = """
            SELECT count(offer.id) 
            FROM offers offer 
            LEFT JOIN tenders tender ON offer.tender_id = tender.id 
            WHERE tender.contractor_id = :contractorId""";
    public static final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(id) FROM offers WHERE tender_id = :tenderId";
    public static final String COUNT_OFFERS_BY_TENDER_ID_IN_QUERY = """
            SELECT tender_id, COUNT(*) as offers 
            FROM offers 
            WHERE tender_id IN (:tenderIds) 
            GROUP BY tender_id""";
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s, %s, %s, %s 
            FROM offers offer 
            LEFT JOIN %s
            LEFT JOIN %s 
            LEFT JOIN %s
            LEFT JOIN %s 
            WHERE offer.id = :id
            """.formatted(
                OfferMixins.OFFER_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_QUERY_COLUMNS,
                CountryMixins.COUNTRY_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                CompanyProfileMixins.COMPANY_PROFILE_JOIN_OFFERS,
                CountryMixins.COUNTRY_JOIN_COMPANY_PROFILES,
                CurrencyMixins.CURRENCY_JOIN_OFFERS,
                FileMetadataMixins.FILE_METADATA_JOIN_OFFERS);;
    
    private final NamedParameterJdbcTemplate jdbc;
    private final OfferMapper offerMapper;
    private final OfferCountExtractor offerCountExtractor;
    private final TenderOfferMapExtractor tenderOfferMapExtractor;

    @Override
    public Offer save(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", offer.getBidder().getId())
                .addValue("tenderId", offer.getTender().getId())
                .addValue("companyProfileId", offer.getCompanyProfile().getId())
                .addValue("globalStatus", offer.getGlobalStatus().name())
                .addValue("bidPrice", offer.getBidPrice())
                .addValue("currencyId", offer.getCurrency().getId())
                .addValue("publicationDate", offer.getPublication())
                .addValue("propositionFileId", offer.getProposition().getId());
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public void update(Offer offer) {
        Integer awardDecisionId = ofNullable(offer.getAwardDecision()).map(AwardDecision::getId).orElse(null);
        Integer rejectDecisionId = ofNullable(offer.getRejectDecision()).map(RejectDecision::getId).orElse(null);
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("globalStatus", offer.getGlobalStatus().name())
                .addValue("awardDecisionId", awardDecisionId)
                .addValue("rejectDecisionId", rejectDecisionId);
        jdbc.update(UPDATE_QUERY, parameters);
    }

    @Override
    public List<Offer> findByBidderWithPagination(Integer bidderId, Integer limit, Integer offset) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bidderId", bidderId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return jdbc.query(FIND_BIDDER_PAGE_QUERY, parameters, offerMapper);
    }

    @Override
    public List<Offer> findByContractorWithPagination(Integer contractorId, Integer limit,
            Integer offset) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("contractorId", contractorId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return jdbc.query(FIND_CONTRACTOR_PAGE_QUERY, parameters, offerMapper);
    }

    @Override
    public List<Offer> findByTenderWithPagination(Integer tenderId, Integer limit, Integer offset) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId)
                .addValue("limit", limit)
                .addValue("offset", offset);
        return jdbc.query(FIND_BY_TENDER_PAGE_QUERY, parameters, offerMapper);
    }

    @Override
    public Map<Integer, Offer> findByBidderIdAndTenderIdIn(Integer bidderId, List<Integer> tenderIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bidderId", bidderId)
                .addValue("tenderIds", tenderIds);
        return jdbc.query(FIND_BY_BIDDER_ID_AND_TENDER_ID_IN_QUERY, parameters, tenderOfferMapExtractor);
    }
    
    @Override
    public List<Offer> findByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId)
                .addValue("statuses", statuses);
        return jdbc.query(FIND_BY_TENDER_ID_AND_GLOBAL_STATUS_IN_PATTERN_QUERY, parameters, offerMapper);
    }
    
    @Override
    public boolean existsByTenderIdAndGlobalStatusIn(Integer tenderId, List<EOfferStatus> statuses) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId)
                .addValue("statuses", statuses);
        return Boolean.TRUE
                .equals(jdbc.queryForObject(EXISTS_BY_TENDER_ID_AND_GLOBAL_STATUS_IN, parameters, Boolean.class));
    }
    
    @Override
    public Integer countByBidder(Integer bidderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bidderId", bidderId);
        return jdbc.queryForObject(COUNT_OFFERS_BY_BIDDER_QUERY, parameters, Integer.class);
    }

    @Override
    public Integer countByContractor(Integer contractorId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("contractorId", contractorId);
        return jdbc.queryForObject(COUNT_OFFERS_BY_CONTRACTOR_QUERY, parameters, Integer.class);
    }

    @Override
    public Integer countAllByTender(Integer tenderId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderId", tenderId);
        return jdbc.queryForObject(COUNT_OFFERS_BY_TENDER_QUERY, parameters, Integer.class);
    }
    
    @Override
    public Map<Integer, Integer> countByTenderIdIn(List<Integer> tenderIds) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("tenderIds", tenderIds);
        return jdbc.query(COUNT_OFFERS_BY_TENDER_ID_IN_QUERY, parameters, offerCountExtractor);
    }
    
    @Override
    public Offer findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, offerMapper);
    }

}