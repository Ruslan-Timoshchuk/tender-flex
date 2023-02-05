package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.mapper.OfferMapper;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;

@Repository
public class OfferDao implements OfferRepository {

    public static final String ADD_NEW_OFFER_QUERY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency, document_name, contractor_status, received_date, bidder_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(o.id) AS offers_amount "
            + "FROM offers o WHERE tender_id = ?";
    public static final String GET_OFFERS_BY_CONTRACTOR_QUERY = "SELECT o.id, o.bidder_id, o.tender_id, cp.id, cp.first_name, "
            + "cp.last_name, cp.phone, org.id, org.organization_name, org.national_registration_number, org.country, org.city, "
            + "o.bid_price, o.currency, o.document_name, o.contractor_status, o.received_date, o.bidder_status "
            + "FROM offers o LEFT JOIN organizations org ON org.id = o.organization_id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id "
            + "LEFT JOIN tenders t ON t.id = o.id WHERE t.contractor_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;
    private final OfferMapper offerMapper;

    public OfferDao(JdbcTemplate jdbcTemplate, OrganizationDao organizationDao, OfferMapper offerMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizationDao = organizationDao;
        this.offerMapper = offerMapper;
    }

    @Override
    public Offer create(Offer offer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Organization organization = organizationDao.create(offer.getOrganization());
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_OFFER_QUERY, new String[] { "id" });
            statement.setInt(1, offer.getBidderId());
            statement.setInt(2, offer.getTender().getId());
            statement.setInt(3, organization.getId());
            statement.setLong(4, offer.getBidPrice());
            statement.setString(5, String.valueOf(offer.getCurrency()));
            statement.setString(6, offer.getDocumentName());
            statement.setString(7, offer.getContractorStatus());
            statement.setObject(8, offer.getReceivedDate());
            statement.setString(9, offer.getBidderStatus());
            return statement;
        }, keyHolder);
        offer.setId(keyHolder.getKeyAs(Integer.class));
        return offer;
    }

    @Override
    public Integer countOffersByTender(Integer tenderId) {
        return jdbcTemplate.queryForObject(COUNT_OFFERS_BY_TENDER_QUERY, Integer.class, tenderId);
    }

    public List<Offer> getOffersByContractor(Integer contractorId) {
        return jdbcTemplate.query(GET_OFFERS_BY_CONTRACTOR_QUERY, offerMapper, contractorId);
    }
}