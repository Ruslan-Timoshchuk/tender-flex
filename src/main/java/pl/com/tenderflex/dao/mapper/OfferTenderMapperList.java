package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;

@Component
public class OfferTenderMapperList implements RowMapper<Offer> {

    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Offer
        .builder()
        .id(resultSet.getInt("id"))
        .bidderId(resultSet.getInt("bidder_id"))
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
        .tender(Tender
                .builder()
                .id(resultSet.getInt("tender_id"))
                .cpvCode(resultSet.getString("cpv_code"))
                .build())
        .bidPrice(resultSet.getInt("bid_price"))
        .publicationDate(resultSet.getObject("publication_date", LocalDate.class))
        .contractorStatus(resultSet.getString("contractor_status"))
        .build();
    }
}