package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.OfferStatus;
import pl.com.tenderflex.model.Organization;

@Component
public class OfferDetailsMapper implements RowMapper<Offer> {

    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Offer
        .builder()
        .id(resultSet.getInt("id"))
        .bidderId(resultSet.getInt("bidder_id"))
        .status(OfferStatus.builder()
                .id(resultSet.getInt("status_id"))
                .contractor(resultSet.getString("contractor"))
                .bidder(resultSet.getString("bidder"))
                .build())
        .organization(Organization
                .builder()
                .id(resultSet.getInt("organization_id"))
                .name(resultSet.getString("organization_name"))
                .nationalRegistrationNumber(resultSet.getString("national_registration_number"))
                .country(Country
                        .builder()
                        .id(resultSet.getInt("country_id"))
                        .countryName(resultSet.getString("country_name"))
                        .build())
                .city(resultSet.getString("city"))
                .contactPerson(ContactPerson
                        .builder()
                        .id(resultSet.getInt("contact_person_id"))
                        .firstName(resultSet.getString("first_name"))
                        .lastName(resultSet.getString("last_name"))
                        .phone(resultSet.getString("phone"))
                        .build())
                .build())
        .bidPrice(resultSet.getInt("bid_price"))
        .currency(Currency
                .builder()
                .id(resultSet.getInt("currency_id"))
                .currencyType(resultSet.getString("currency_type"))
                .build())
        .documentName(resultSet.getString("document_name"))
        .awardDecision(resultSet.getString("award_decision_name"))
        .rejectDecision(resultSet.getString("reject_decision_name"))
        .build();
    }
}