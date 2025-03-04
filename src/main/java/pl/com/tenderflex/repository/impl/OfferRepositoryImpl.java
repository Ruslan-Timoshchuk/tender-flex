package pl.com.tenderflex.repository.impl;

import static java.util.stream.Collectors.toSet;
import static org.springframework.dao.support.DataAccessUtils.*;
import static java.lang.String.format;
import static java.util.Optional.*;
import static pl.com.tenderflex.repository.impl.TenderRepositoryImpl.*;
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
    public static final String ADD_NEW_OFFER_QUERY = """
            INSERT INTO offers(bidder_id, tender_id, company_profile_id, global_status, 
                               bid_price, currency_id, publication_date, proposition_file_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s, %s FROM offers offer %s %s WHERE offer.id = ?";
    public static final String SELECT_BY_TENDER_AND_BIDDER_PATTERN_QUERY = "SELECT %s, %s FROM offers offer %s %s WHERE offer.tender_id = ? AND bidder_id = ?";
    public static final String GET_OFFERS_BY_BIDDER_QUERY = "SELECT o.id, bidder_id, tender_id, official_name, registration_number, "
            + "country_id, country_name, city, first_name, last_name, phone_number, bid_price, currency_id, currency_type, publication_date, "
            + "document_name, offer_status_bidder, offer_status_contractor "
            + "FROM offers o "
            + "LEFT JOIN countries co ON co.id = o.country_id "
            + "LEFT JOIN currencies cur ON cur.id = o.currency_id "
            + "WHERE bidder_id = ? LIMIT ? OFFSET ?";
    public static final String GET_OFFERS_BY_CONTRACTOR_QUERY = "SELECT o.id, bidder_id, tender_id, o.official_name, o.registration_number, "
            + "o.country_id, country_name, o.city, o.first_name, o.last_name, o.phone_number, bid_price, o.currency_id, currency_type, o.publication_date, "
            + "document_name, offer_status_bidder, offer_status_contractor "
            + "FROM offers o "
            + "LEFT JOIN countries c ON c.id = o.country_id "
            + "LEFT JOIN currencies cur ON cur.id = o.currency_id "
            + "LEFT JOIN tenders t ON t.id = tender_id "
            + "WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String GET_OFFERS_BY_TENDER_QUERY = "SELECT o.id, bidder_id, tender_id, o.official_name, o.registration_number, "
            + "o.country_id, country_name, o.city, o.first_name, o.last_name, o.phone_number, bid_price, o.currency_id, currency_type, "
            + "o.publication_date, document_name, offer_status_bidder, offer_status_contractor "
            + "FROM offers o "
            + "LEFT JOIN countries c ON c.id = o.country_id "
            + "LEFT JOIN currencies cur ON cur.id = o.currency_id "
            + "WHERE o.tender_id = ?";
    public static final String COUNT_OFFERS_BY_BIDDER = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public static final String COUNT_OFFERS_BY_CONTRACTOR = "SELECT count(o.id) FROM offers o LEFT JOIN tenders t ON o.tender_id = t.id WHERE contractor_id = ?";
    public static final String COUNT_OFFERS_BY_TENDER = "SELECT count(os.id) FROM offers os WHERE os.tender_id = ?";
 
    public static final String ADD_AWARD_DECISION_QUERY = "UPDATE offers SET award_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String ADD_REJECT_DECISION_QUERY = "UPDATE offers SET reject_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String UPDATE_OFFER_STATUS_QUERY = "UPDATE offers SET status_id = ? WHERE id = ?";
    public static final String UPDATE_OFFERS_STATUS_QUERY = "UPDATE offers SET status_id = ?, "
            + "reject_decision_name = ? WHERE tender_id = ? AND status_id <= ? AND id != ?";

    public static final String OFFER_COLUMNS_SQL_PART_QUERY = """
            offer.id AS offer_id, offer.global_status AS offer_global_status, 
            offer.bid_price AS offer_bid_price, offer.publication_date AS offer_publication_date,
            bidder_profile.id AS bidder_profile_id, bidder_profile.official_name AS bidder_official_name,
            bidder_profile.registration_number AS bidder_registration_number,
            bidder_country.id AS bidder_country_id, bidder_country.name AS bidder_country_name,
            bidder_country.iso_code AS bidder_country_iso_code, bidder_country.phone_code AS bidder_country_phone_code,
            bidder_profile.city AS bidder_city, bidder_profile.contact_first_name AS bidder_contact_first_name,
            bidder_profile.contact_last_name AS bidder_contact_last_name, bidder_profile.contact_phone_number AS bidder_contact_phone_number,
            offer_currency.id AS offer_currency_id, offer_currency.code AS offer_currency_code, offer_currency.symbol AS offer_currency_symbol,
            proposition_file.id AS proposition_file_id, proposition_file.name AS proposition_file_name,
            proposition_file.content_type AS proposition_file_content_type, proposition_file.aws_s3_file_key AS proposition_aws_s3_file_key""";
    public static final String OFFER_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN company_profiles bidder_profile ON bidder_profile.id = offer.company_profile_id
            LEFT JOIN countries bidder_country ON bidder_country.id = bidder_profile.country_id
            LEFT JOIN currencies offer_currency ON offer_currency.id = offer.currency_id
            LEFT JOIN files proposition_file ON proposition_file.id = offer.proposition_file_id
            LEFT JOIN tenders tender ON tender.id = offer.tender_id""";
    
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
    public Set<Offer>getPageByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_BIDDER_QUERY, offerMapper, bidderId, amountOffers,
                amountOffersToSkip).stream().collect(toSet());
    }

    @Override
    public Set<Offer> getPageByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_CONTRACTOR_QUERY, offerMapper, contractorId, amountOffers,
                amountOffersToSkip).stream().collect(toSet());
    }
    
    @Override
    public Set<Offer> getByTender(Integer tenderId) {
        return jdbcTemplate.query(GET_OFFERS_BY_TENDER_QUERY, offerMapper, tenderId).stream()
                .collect(toSet());
    }

    @Override
    public Integer countOffersByBidder(Integer bidderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_BIDDER, Integer.class, bidderId);
    }

    @Override
    public Integer countOffersByContractor(Integer contractorId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_CONTRACTOR, Integer.class, contractorId);
    }

    @Override
    public Integer countOffersByTender(Integer tenderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_TENDER, Integer.class, tenderId);
    }
   
    @Override
    public Offer findById(Integer offerId) {
        String sqlQuery = format(SELECT_BY_ID_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                TENDER_COLUMNS_SQL_PART_QUERY, OFFER_JOIN_TABLES_SQL_PART_QUERY, TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return jdbcTemplate.queryForObject(sqlQuery, offerMapper, offerId);
    }
    
    @Override
    public Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId) {
        String sqlQuery = format(SELECT_BY_TENDER_AND_BIDDER_PATTERN_QUERY, OFFER_COLUMNS_SQL_PART_QUERY,
                TENDER_COLUMNS_SQL_PART_QUERY, OFFER_JOIN_TABLES_SQL_PART_QUERY, TENDER_JOIN_TABLES_SQL_PART_QUERY);
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, sqlQuery);
        return ofNullable(singleResult(jdbcTemplate.query(sqlQuery, offerMapper, tenderId, bidderId)));
    }
        
    @Override
    public void addAwardDecision(String awardDecision, Integer stageStatus, Integer offerId) {
        jdbcTemplate.update(ADD_AWARD_DECISION_QUERY, awardDecision, stageStatus, offerId);
    }

    @Override
    public void addRejectDecision(String rejectDecision, Integer stageStatus, Integer offerId) {
        jdbcTemplate.update(ADD_REJECT_DECISION_QUERY, rejectDecision, stageStatus, offerId);
    }

    @Override
    public void updateOfferStatus(Integer statusId, Integer offerId) {
        jdbcTemplate.update(UPDATE_OFFER_STATUS_QUERY, statusId, offerId);
    }

    @Override
    public void updateOffersStatus(Integer statusId, String rejectDecisionName, Integer tenderId,
            Integer statusOfActiveOffers, Integer offerId) {
        jdbcTemplate.update(UPDATE_OFFERS_STATUS_QUERY, statusId, rejectDecisionName, tenderId, statusOfActiveOffers, offerId);
    }
}