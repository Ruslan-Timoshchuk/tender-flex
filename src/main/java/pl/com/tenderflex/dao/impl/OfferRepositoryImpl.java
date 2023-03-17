package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.mapper.OfferBidderMapperList;
import pl.com.tenderflex.dao.mapper.OfferDetailsMapper;
import pl.com.tenderflex.model.Offer;

@Repository
@RequiredArgsConstructor
public class OfferRepositoryImpl implements OfferRepository {

    public static final String ADD_NEW_OFFER_QURY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency_id, contractor_status, bidder_status, publication_date, document_url) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_OFFERS_BY_BIDDER_QUERY = "SELECT os.id, os.bidder_id, os.organization_id, org.organization_name, "
            + "org.country_id, co.country_name, os.tender_id, ten.cpv_code, os.bid_price, os.publication_date, os.bidder_status "
            + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries co ON co.id = org.country_id "
            + "LEFT JOIN tenders ten ON ten.id = os.tender_id WHERE bidder_id = ? LIMIT ? OFFSET ?";
    public static final String COUNT_OFFERS_BY_BIDDER = "SELECT count(id) FROM offers WHERE bidder_id = ?";
    public static final String GET_OFFER_BY_ID_QUERY = "SELECT os.id, os.bidder_id, os.organization_id, org.organization_name, "
            + "org.national_registration_number, org.country_id, cs.country_name, org.city, org.contact_person_id, cp.first_name, "
            + "cp.last_name, cp.phone, os.bid_price, os.currency_id, cur.currency_type, os.document_url "
            + "FROM offers os "
            + "LEFT JOIN organizations org ON org.id = os.organization_id "
            + "LEFT JOIN countries cs ON cs.id = org.country_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN currencies cur ON cur.id = os.currency_id "
            + "WHERE os.id = ?";
    
    private final JdbcTemplate jdbcTemplate;
    private final OfferBidderMapperList offerBidderMapperList;
    private final OfferDetailsMapper offerDetailsMapper;
    
    @Override
    public Offer create(Offer offer, Integer bidderId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QURY, new String[] { "id" });
            statement.setInt(1, bidderId);
            statement.setInt(2, offer.getTender().getId());
            statement.setInt(3, offer.getOrganization().getId());
            statement.setLong(4, offer.getBidPrice());
            statement.setInt(5, offer.getCurrency().getId());
            statement.setString(6, offer.getContractorStatus());
            statement.setString(7, offer.getBidderStatus());
            statement.setObject(8, offer.getPublicationDate());
            statement.setString(9, offer.getDocumentUrl());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public List<Offer> getByBidder(Integer bidderId, Integer amountOffers, Integer amountOffersToSkip) {
        return jdbcTemplate.query(GET_OFFERS_BY_BIDDER_QUERY, offerBidderMapperList, bidderId, amountOffers,
                amountOffersToSkip);
    }

    @Override
    public Integer countOffersByBidder(Integer bidderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_BIDDER, Integer.class, bidderId);
    }
    
    @Override
    public Offer getById(Integer offerId) {
        return jdbcTemplate.queryForObject(GET_OFFER_BY_ID_QUERY, offerDetailsMapper, offerId);
    }
}