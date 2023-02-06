package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Component
public class OfferContractorMapper implements RowMapper<Offer> {

    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(resultSet.getInt("id"));
        Organization organization = new Organization();
        organization.setName(resultSet.getString("organization_name"));
        Tender tender = new Tender();
        tender.setCpvCode(resultSet.getString("cpv_code"));
        offer.setTender(tender);
        offer.setBidPrice(resultSet.getInt("bid_price"));
        offer.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        organization.setCountry(Country.valueOf(resultSet.getString("country")));
        offer.setOrganization(organization);
        offer.setReceivedDate(resultSet.getObject("received_date", LocalDate.class));
        offer.setContractorStatus(resultSet.getString("contractor_status"));
        return offer;
    }
}