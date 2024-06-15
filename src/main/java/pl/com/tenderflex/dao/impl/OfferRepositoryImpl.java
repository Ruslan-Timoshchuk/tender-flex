package pl.com.tenderflex.dao.impl;

import static java.util.stream.Collectors.toSet;
import static java.util.Optional.*;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.mapper.OfferMapper;
import pl.com.tenderflex.dao.mapper.TotalMapper;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Total;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    public static final String ADD_NEW_OFFER_QUERY = "INSERT INTO offers(bidder_id, tender_id, official_name, registration_number, "
            + "country_id, city, first_name, last_name, phone_number, bid_price, currency_id, publication_date, document_name, "
            + "offer_status_bidder, offer_status_contractor) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
    public static final String GET_OFFERS_BY_TENDER_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ofst.contractor, ofst.bidder, "
            + "os.organization_id, org.organization_name, org.country_id, co.country_name, os.tender_id, cs.description, "
            + "os.bid_price, os.currency_id, cur.currency_type, os.publication_date, "
            + "COALESCE(os.award_decision_name, 'OFFER IS NOT AWARDED') AS award_decision_name, "
            + "COALESCE(os.reject_decision_name, 'OFFER IS NOT REJECTED') AS reject_decision_name " + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id " + "LEFT JOIN tenders ten ON ten.id = os.tender_id "
            + "LEFT JOIN offer_statuses ofst ON ofst.id = os.status_id " + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "LEFT JOIN currencies cur ON cur.id = os.currency_id "
            + "WHERE tender_id = ? LIMIT ? OFFSET ?";
    public static final String COUNT_OFFERS_BY_BIDDER = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public static final String COUNT_OFFERS_BY_CONTRACTOR = "SELECT count(os.id) FROM offers os "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id WHERE contractor_id = ?";
    public static final String COUNT_OFFERS_BY_TENDER = "SELECT count(id) FROM offers WHERE tender_id = ?";
    public static final String COUNT_ACTIVE_OFFERS_BY_TENDER = "SELECT count(id) FROM offers WHERE tender_id = ? AND status_id <= ?";
    public static final String GET_OFFER_BY_ID_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ost.contractor, ost.bidder, "
            + "os.organization_id, org.organization_name, org.national_registration_number, org.country_id, cs.country_name, "
            + "org.city, org.contact_person_id, cp.first_name, cp.last_name, cp.phone, os.bid_price, os.currency_id, cur.currency_type, "
            + "os.document_name, os.award_decision_name, os.reject_decision_name " + "FROM offers os "
            + "LEFT JOIN offer_statuses ost ON ost.id = os.status_id "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries cs ON cs.id = org.country_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN currencies cur ON cur.id = os.currency_id " + "WHERE os.id = ?";
    public static final String GET_OFFER_BY_TENDER_AND_BIDDER_QUERY = "SELECT o.id, bidder_id, tender_id, official_name, registration_number, "
            + "country_id, country_name, city, first_name, last_name, phone_number, bid_price, currency_id, currency_type, publication_date, "
            + "document_name, offer_status_bidder, offer_status_contractor "
            + "FROM offers o "
            + "LEFT JOIN countries c ON c.id = o.country_id "
            + "LEFT JOIN currencies cur ON cur.id = o.currency_id "
            + "WHERE tender_id = ? AND bidder_id = ?";
    public static final String GET_TOTAL_BY_BIDDER_QUERY = "SELECT (SELECT COUNT(id) FROM tenders) as tenders, "
            + "(SELECT COUNT(id) from offers WHERE bidder_id = ?) as offers";
    public static final String ADD_AWARD_DECISION_QUERY = "UPDATE offers SET award_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String ADD_REJECT_DECISION_QUERY = "UPDATE offers SET reject_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String UPDATE_OFFER_STATUS_QUERY = "UPDATE offers SET status_id = ? WHERE id = ?";
    public static final String UPDATE_OFFERS_STATUS_QUERY = "UPDATE offers SET status_id = ?, "
            + "reject_decision_name = ? WHERE tender_id = ? AND status_id <= ? AND id != ?";

    private final JdbcTemplate jdbcTemplate;
    private final OfferMapper offerMapper;
    private final TotalMapper totalMapper;

    @Override
    public Offer create(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QUERY, new String[] { "id" });
            statement.setInt(1, offer.getBidder().getId());
            statement.setInt(2, offer.getTender().getId());
            statement.setString(3, offer.getBidderCompanyDetails().getOfficialName());
            statement.setString(4, offer.getBidderCompanyDetails().getRegistrationNumber());
            statement.setInt(5, offer.getBidderCompanyDetails().getCountry().getId());
            statement.setString(6, offer.getBidderCompanyDetails().getCity());
            statement.setString(7, offer.getContactPerson().getFirstName());
            statement.setString(8, offer.getContactPerson().getLastName());
            statement.setString(9, offer.getContactPerson().getPhoneNumber());
            statement.setLong(10, offer.getBidPrice());
            statement.setInt(11, offer.getCurrency().getId());
            statement.setObject(12, offer.getPublicationDate());
            statement.setString(13, offer.getDocumentName());
            statement.setString(14, offer.getOfferStatusBidder().name());
            statement.setString(15, offer.getOfferStatusContractor().name());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }
    
    @Override
    public Set<Offer>getPageByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_BIDDER_QUERY, offerMapper, bidderId, amountOffers,
                amountOffersToSkip).stream().collect(toSet()) ;
    }

    @Override
    public List<Offer> getByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_CONTRACTOR_QUERY, offerMapper, contractorId, amountOffers,
                amountOffersToSkip);
    }

    @Override
    public List<Offer> getByTender(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_TENDER_QUERY, offerMapper, tenderId, amountOffers,
                amountOffersToSkip);
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
    public Integer countActiveOffersByTender(Integer tenderId, Integer activeOfferStatusId) {
        return jdbcTemplate.queryForObject(COUNT_ACTIVE_OFFERS_BY_TENDER, Integer.class, tenderId, activeOfferStatusId);
    }

    @Override
    public Offer getById(Integer offerId) {
        return jdbcTemplate.queryForObject(GET_OFFER_BY_ID_QUERY, offerMapper, offerId);
    }
    
    @Override
    public Optional<Offer> findOfferByTenderAndBidder(Integer tenderId, Integer bidderId) {
        try {
            return of(jdbcTemplate.queryForObject(GET_OFFER_BY_TENDER_AND_BIDDER_QUERY, offerMapper, tenderId, bidderId));
        } catch (EmptyResultDataAccessException e) {
            return empty();
        }
    }

    @Override
    public Total getTotalTendersAndOffersByBidder(Integer bidderId) {
        return jdbcTemplate.queryForObject(GET_TOTAL_BY_BIDDER_QUERY, totalMapper, bidderId);
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