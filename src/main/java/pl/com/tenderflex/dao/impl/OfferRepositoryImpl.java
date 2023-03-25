package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.mapper.OfferMapperList;
import pl.com.tenderflex.dao.mapper.OfferDetailsMapper;
import pl.com.tenderflex.dao.mapper.TotalMapper;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Total;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    public static final String ADD_NEW_OFFER_QURY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency_id, publication_date, document_name) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_OFFERS_BY_BIDDER_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ofst.contractor, "
            + "ofst.bidder, os.organization_id, org.organization_name, org.country_id, co.country_name, os.tender_id, "
            + "cs.description, os.bid_price, os.publication_date, "
            + "COALESCE(os.award_decision_name, 'OFFER IS NOT AWARDED') AS award_decision_name, "
            + "COALESCE(os.reject_decision_name, 'OFFER IS NOT REJECTED') AS reject_decision_name "
            + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id "
            + "LEFT JOIN offer_statuses ofst ON ofst.id = os.status_id "
            + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "WHERE bidder_id = ? LIMIT ? OFFSET ?";
    public static final String GET_OFFERS_BY_CONTRACTOR_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ofst.contractor, "
            + "ofst.bidder, os.organization_id, org.organization_name, org.country_id, co.country_name, os.tender_id, "
            + "cs.description, os.bid_price, os.publication_date, "
            + "COALESCE(os.award_decision_name, 'OFFER IS NOT AWARDED') AS award_decision_name, "
            + "COALESCE(os.reject_decision_name, 'OFFER IS NOT REJECTED') AS reject_decision_name "
            + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id "
            + "LEFT JOIN offer_statuses ofst ON ofst.id = os.status_id "
            + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "WHERE contractor_id = ? LIMIT ? OFFSET ?";
    public static final String GET_OFFERS_BY_TENDER_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ofst.contractor, ofst.bidder, os.organization_id, "
            + "org.organization_name, org.country_id, co.country_name, os.tender_id, cs.description, os.bid_price, os.publication_date, "
            + "COALESCE(os.award_decision_name, 'OFFER IS NOT AWARDED') AS award_decision_name, "
            + "COALESCE(os.reject_decision_name, 'OFFER IS NOT REJECTED') AS reject_decision_name "
            + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id "
            + "LEFT JOIN offer_statuses ofst ON ofst.id = os.status_id "
            + "LEFT JOIN cpvs cs ON cs.id = ten.cpv_id "
            + "WHERE tender_id = ? LIMIT ? OFFSET ?";
    public static final String COUNT_OFFERS_BY_BIDDER = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public static final String COUNT_OFFERS_BY_CONTRACTOR = "SELECT count(os.id) FROM offers os "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id WHERE contractor_id = ?";
    public static final String COUNT_OFFERS_BY_TENDER = "SELECT count(id) FROM offers WHERE tender_id = ?";
    public static final String GET_OFFER_BY_ID_QUERY = "SELECT os.id, os.bidder_id, os.status_id, ost.contractor, ost.bidder, "
            + "os.organization_id, org.organization_name, org.national_registration_number, org.country_id, cs.country_name, "
            + "org.city, org.contact_person_id, cp.first_name, cp.last_name, cp.phone, os.bid_price, os.currency_id, cur.currency_type, "
            + "os.document_name, os.award_decision_name, os.reject_decision_name "
            + "FROM offers os "
            + "LEFT JOIN offer_statuses ost ON ost.id = os.status_id "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries cs ON cs.id = org.country_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN currencies cur ON cur.id = os.currency_id "
            + "WHERE os.id = ?";
    public static final String OFFER_IS_EXISTS_BY_TENDER_AND_BIDDER_QUERY = "SELECT EXISTS(SELECT 1 FROM offers "
            + "WHERE tender_id = ? AND bidder_id = ?)";
    public static final String GET_TOTAL_BY_BIDDER_QUERY = "SELECT (SELECT COUNT(id) FROM tenders) as tenders, "
            + "(SELECT COUNT(id) from offers WHERE bidder_id = ?) as offers";
    public static final String ADD_AWARD_DECISION_QUERY = "UPDATE offers SET award_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String ADD_REJECT_DECISION_QUERY = "UPDATE offers SET reject_decision_name = ?, status_id = ? WHERE id = ?";
    public static final String UPDATE_OFFER_STATUS_QUERY = "UPDATE offers SET status_id = ? WHERE id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final OfferMapperList offerMapperList;
    private final OfferDetailsMapper offerDetailsMapper;
    private final TotalMapper totalMapper;
    
    @Override
    public Offer create(Offer offer, Integer bidderId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QURY, new String[] { "id" });
            statement.setInt(1, bidderId);
            statement.setInt(2, offer.getTenderId());
            statement.setInt(3, offer.getOrganization().getId());
            statement.setLong(4, offer.getBidPrice());
            statement.setInt(5, offer.getCurrency().getId());   
            statement.setObject(6, offer.getPublicationDate());
            statement.setString(7, offer.getDocumentName());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public List<Offer> getByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_BIDDER_QUERY, offerMapperList, bidderId, amountOffers,
                amountOffersToSkip);
    }
   
    @Override
    public List<Offer> getByContractor(Integer contractorId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_CONTRACTOR_QUERY, offerMapperList, contractorId, amountOffers,
                amountOffersToSkip);
    }
    
    @Override
    public List<Offer> getByTender(Integer tenderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_TENDER_QUERY, offerMapperList, tenderId, amountOffers,
                amountOffersToSkip);
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
    public Offer getById(Integer offerId) {
        return jdbcTemplate.queryForObject(GET_OFFER_BY_ID_QUERY, offerDetailsMapper, offerId);
    }
    
    @Override
    public boolean isExistsOfferByTenderAndBidder(Integer tenderId, Integer bidderId) {
        return jdbcTemplate.queryForObject(OFFER_IS_EXISTS_BY_TENDER_AND_BIDDER_QUERY, Boolean.class, tenderId, bidderId);
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
    public void updateOfferStatus(Integer stageStatus, Integer offerId) {
        jdbcTemplate.update(UPDATE_OFFER_STATUS_QUERY, stageStatus, offerId); 
    }
}