package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;

@Repository
public class OfferDao implements OfferRepository {

    public static final String ADD_NEW_OFFER_QUERY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency, document_name, contractor_status, bidder_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(o.id) AS amount_offers "
            + "FROM offers o WHERE tender_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;

    public OfferDao(JdbcTemplate jdbcTemplate, OrganizationDao organizationDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizationDao = organizationDao;
    }

    @Override
    public Offer create(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Organization organization = organizationDao.create(offer.getOrganization());
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QUERY, new String[] { "id" });
            statement.setInt(1, offer.getBidderId());
            statement.setInt(2, offer.getTenderId());
            statement.setInt(3, organization.getId());
            statement.setLong(4, offer.getBidPrice());
            statement.setString(5, String.valueOf(offer.getCurrency()));
            statement.setString(6, offer.getDocumentName());
            statement.setString(7, offer.getContractorStatus());
            statement.setString(8, offer.getBidderStatus());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public Integer countOffersByTender(Integer tenderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_TENDER_QUERY, Integer.class, tenderId);
    }
}