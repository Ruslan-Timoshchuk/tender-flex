package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import pl.com.tenderflex.dao.impl.TenderDao;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Offer;

@Component
public class OfferMapper implements RowMapper<Offer> {

    private final TenderDao tenderDao;
    private final OrganizationMapper organizationMapper;

    public OfferMapper(TenderDao tenderDao, OrganizationMapper organizationMapper) {
        this.tenderDao = tenderDao;
        this.organizationMapper = organizationMapper;
    }

    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(resultSet.getInt("id"));
        offer.setBidderId(resultSet.getInt("bidder_id"));
        offer.setTender(tenderDao.getById(resultSet.getInt("tender_id")));
        offer.setOrganization(organizationMapper.mapRow(resultSet, rowNum));
        offer.setBidPrice(resultSet.getInt("bid_price"));
        offer.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        offer.setDocumentName(resultSet.getString("document_name"));
        offer.setContractorStatus(resultSet.getString("contractor_status"));
        offer.setReceivedDate(resultSet.getObject("received_date", LocalDate.class));
        offer.setBidderStatus(resultSet.getString("bidder_status"));
        return offer;
    }
}