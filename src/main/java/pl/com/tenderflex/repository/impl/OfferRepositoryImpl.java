package pl.com.tenderflex.repository.impl;

import static java.util.stream.Collectors.toSet;
import static org.springframework.dao.support.DataAccessUtils.*;
import static java.lang.String.format;
import static java.util.Optional.*;
import java.sql.PreparedStatement;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.repository.OfferRepository;
import pl.com.tenderflex.repository.mapper.OfferMapper;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferRepositoryImpl.class);

    public static final String EXECUTING_SQL_QUERY_LOG = "Executing SQL Query: {}";
    public static final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.id = ?";
    public static final String SELECT_BY_TENDER_AND_BIDDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.tender_id = ? AND bidder_id = ?";
    public static final String SELECT_PAGE_BY_BIDDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE bidder_id = ? LIMIT ? OFFSET ?";
    public static final String SELECT_PAGE_BY_CONTRACTOR_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String SELECT_PAGE_BY_TENDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.tender_id = ? LIMIT ? OFFSET ?";
    public static final String SELECT_ALL_BY_TENDER_PATTERN_QUERY = "SELECT %s FROM offers offer %s WHERE offer.tender_id = ?";

    public static final String OFFER_COLUMNS_SQL_PART_QUERY = """
            offer.id AS offer_id, offer.tender_id, offer.global_status, offer.bid_price, offer.publication_date,
            offer.company_profile_id, company_profile.official_name, company_profile.registration_number,
            company_profile.country_id, country.name, country.iso_code, country.phone_code, company_profile.city,
            company_profile.contact_first_name, company_profile.contact_last_name, company_profile.contact_phone_number,
            offer.currency_id, currency.code, currency.symbol, proposition_file.id AS proposition_file_id,
            proposition_file.name AS proposition_file_name, proposition_file.content_type AS proposition_file_content_type,
            proposition_file.aws_s3_file_key AS proposition_file_aws_s3_file_key, contract.id AS contract_id,
            offer.award_decision_id AS award_id, offer.reject_decision_id AS reject_id""";
    public static final String OFFER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN company_profiles company_profile ON company_profile.id = offer.company_profile_id
            LEFT JOIN countries country ON country.id = company_profile.country_id
            LEFT JOIN currencies currency ON currency.id = offer.currency_id
            LEFT JOIN files proposition_file ON proposition_file.id = offer.proposition_file_id
            LEFT JOIN tenders tender ON tender.id = offer.tender_id
            LEFT JOIN contracts contract ON contract.offer_id = offer.id""";

    public static final String ADD_NEW_OFFER_QUERY = """
            INSERT INTO offers(bidder_id, tender_id, company_profile_id, global_status,
                               bid_price, currency_id, publication_date, proposition_file_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String UPDATE_OFFER_QUERY = """
            UPDATE offers
            SET global_status = ?, award_decision_id = ?, reject_decision_id = ?
            WHERE id = ?""";
    public static final String COUNT_OFFERS_BY_BIDDER_QUERY = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public static final String COUNT_OFFERS_BY_CONTRACTOR_QUERY = "SELECT count(o.id) FROM offers o LEFT JOIN tenders t ON o.tender_id = t.id WHERE contractor_id = ?";
    public static final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(os.id) FROM offers os WHERE os.tender_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OfferMapper offerMapper;

    @Override
    public Offer save(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QUERY, new String[] { "id" });
            statement.setInt(1, offer.getBidderId());
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
        jdbcTemplate.update(UPDATE_OFFER_QUERY, offer.getGlobalStatus().name(), offer.getAwardDecision().getId(),
                offer.getRejectDecision().getId(), offer.getId());
    }

    @Override
    public Set<Offer> findByBidderWithPagination(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip) {
        String sqlQuery = format(SELECT_PAGE_BY_BIDDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, offerMapper, bidderId, amountOffers, amountOffersToSkip).stream()
                .collect(toSet());
    }

    @Override
    public Set<Offer> findByContractorWithPagination(Integer contractorId, Integer amountOffers,
            Integer amountOffersToSkip) {
        String sqlQuery = format(SELECT_PAGE_BY_CONTRACTOR_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, offerMapper, contractorId, amountOffers, amountOffersToSkip).stream()
                .collect(toSet());
    }

    @Override
    public Set<Offer> findByTenderWithPagination(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip) {
        String sqlQuery = format(SELECT_PAGE_BY_TENDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, offerMapper, tenderId, amountOffers, amountOffersToSkip).stream()
                .collect(toSet());
    }

    @Override
    public Set<Offer> findAllByTender(Integer id) {
        String sqlQuery = format(SELECT_ALL_BY_TENDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.query(sqlQuery, offerMapper, id).stream()
                .collect(toSet());
    }
    
    @Override
    public Integer countOffersByBidder(Integer bidderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_BIDDER_QUERY, Integer.class, bidderId);
    }

    @Override
    public Integer countOffersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_CONTRACTOR_QUERY, Integer.class, contractorId);
    }

    @Override
    public Integer countOffersByTender(Integer tenderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_TENDER_QUERY, Integer.class, tenderId);
    }

    @Override
    public Offer findById(Integer offerId) {
        String sqlQuery = format(SELECT_BY_ID_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.queryForObject(sqlQuery, offerMapper, offerId);
    }

    @Override
    public Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId) {
        String sqlQuery = format(SELECT_BY_TENDER_AND_BIDDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                OFFER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return ofNullable(singleResult(jdbcTemplate.query(sqlQuery, offerMapper, tenderId, bidderId)));
    }

}