package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;

@Component
public class OfferDetailsMapper implements RowMapper<Offer> {

    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Offer offer = new Offer();
        offer.setId(resultSet.getInt("id"));
        Organization organization = new Organization();
        organization.setName(resultSet.getString("organization_name"));
        organization.setNationalRegistrationNumber(resultSet.getString("national_registration_number"));
        organization.setCountry(Country.valueOf(resultSet.getString("country")));
        organization.setCity(resultSet.getString("city"));
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setFirstName(resultSet.getString("first_name"));
        contactPerson.setLastName(resultSet.getString("last_name"));
        contactPerson.setPhone(resultSet.getString("phone"));
        organization.setContactPerson(contactPerson);
        offer.setOrganization(organization);
        offer.setBidPrice(resultSet.getInt("bid_price"));
        offer.setCurrency(Currency.valueOf(resultSet.getString("currency")));
        offer.setDocumentName(resultSet.getString("document_name"));
        return offer;
    }
}