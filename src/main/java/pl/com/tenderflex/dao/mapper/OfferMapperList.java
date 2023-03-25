package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.OfferStatus;
import pl.com.tenderflex.model.Organization;

@Component
public class OfferMapperList implements RowMapper<Offer> {

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
                .country(Country
                        .builder()
                        .id(resultSet.getInt("country_id"))
                        .countryName(resultSet.getString("country_name"))
                        .build())
                .build())
        .tenderId(resultSet.getInt("tender_id"))
        .fieldOfTheTender(resultSet.getString("description"))
        .bidPrice(resultSet.getInt("bid_price"))
        .publicationDate(resultSet.getObject("publication_date", LocalDate.class))
        .awardDecision(resultSet.getString("award_decision_name"))
        .rejectDecision(resultSet.getString("reject_decision_name"))
        .build();
    }
}