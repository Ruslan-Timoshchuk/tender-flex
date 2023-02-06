package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import pl.com.tenderflex.dao.OfferRepository;
import pl.com.tenderflex.dao.mapper.OfferContractorMapper;
import pl.com.tenderflex.dao.mapper.OfferDetailsMapper;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;

@Repository
public class OfferDao implements OfferRepository {

    public static final String ADD_NEW_OFFER_QUERY = "INSERT INTO offers(bidder_id, tender_id, organization_id, "
            + "bid_price, currency, document_name, contractor_status, received_date, bidder_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String COUNT_OFFERS_BY_TENDER_QUERY = "SELECT count(o.id) AS offers_amount "
            + "FROM offers o WHERE tender_id = ?";
    public static final String GET_OFFERS_BY_CONTRACTOR_QUERY = "SELECT o.id, org.organization_name, t.cpv_code, o.bid_price, "
            + "o.currency, org.country, received_date, o.contractor_status FROM offers o "
            + "LEFT JOIN organizations org ON org.id = o.organization_id "
            + "LEFT JOIN tenders t ON t.id = o.tender_id WHERE t.contractor_id = ?";
    public static final String GET_OFFER_BY_ID_QUERY = "SELECT o.id, organization_name, national_registration_number, country, city, "
            + "first_name, last_name, phone, bid_price, currency, document_name FROM offers o LEFT JOIN organizations org ON org.id = o.id "
            + "LEFT JOIN contact_persons cp ON cp.id = org.contact_person_id WHERE o.id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final OrganizationDao organizationDao;
    private final OfferContractorMapper offerContractorMapper;
    private final OfferDetailsMapper offerDetailsMapper;

    public OfferDao(JdbcTemplate jdbcTemplate, OrganizationDao organizationDao,
            OfferContractorMapper offerContractorMapper, OfferDetailsMapper offerDetailsMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.organizationDao = organizationDao;
        this.offerContractorMapper = offerContractorMapper;
        this.offerDetailsMapper = offerDetailsMapper;
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

    @Override
    public List<Offer> getOffersByContractor(Integer contractorId) {
        return jdbcTemplate.query(GET_OFFERS_BY_CONTRACTOR_QUERY, offerContractorMapper, contractorId);
    }

    @Override
    public Offer getById(Integer offerId) {
        return jdbcTemplate.queryForObject(GET_OFFER_BY_ID_QUERY, offerDetailsMapper, offerId);
    }
}