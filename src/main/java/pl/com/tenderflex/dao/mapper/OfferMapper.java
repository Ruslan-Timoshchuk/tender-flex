package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.EOfferStatus;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.dao.impl.TenderRepositoryImpl;
import pl.com.tenderflex.dao.impl.UserRepositoryImpl;
import pl.com.tenderflex.model.CompanyDetails;
import pl.com.tenderflex.model.ContactPerson;

@Component
@RequiredArgsConstructor
public class OfferMapper implements RowMapper<Offer> {

    private final UserRepositoryImpl userRepository;
    private final TenderRepositoryImpl tenderRepository;
    
    @Override
    public Offer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Offer
                 .builder()
                 .id(resultSet.getInt("id"))
                 .bidder(userRepository.getById(resultSet.getInt("bidder_id")))
                 .tender(tenderRepository.getById(resultSet.getInt("tender_id")))
                 .bidderCompanyDetails(mapCompanyDetails(resultSet))
                 .contactPerson(mapContactPerson(resultSet))
                 .bidPrice(resultSet.getInt("bid_price"))
                 .currency(mapCurrency(resultSet))
                 .publicationDate(resultSet.getObject("publication_date", LocalDate.class))
                 .documentName(resultSet.getString("document_name"))
                 .offerStatusBidder(EOfferStatus.valueOf(resultSet.getString("offer_status_bidder")))
                 .offerStatusContractor(EOfferStatus.valueOf(resultSet.getString("offer_status_contractor")))
               .build();
    }
    
    private CompanyDetails mapCompanyDetails(ResultSet resultSet) throws SQLException {
        return CompanyDetails.builder()
                .officialName(resultSet.getString("official_name"))
                .registrationNumber(resultSet.getString("registration_number"))
                .country(mapCountry(resultSet))
                .city(resultSet.getString("city"))
                .build();
    }
    
    private Country mapCountry(ResultSet resultSet) throws SQLException {
        return Country.builder()
                .id(resultSet.getInt("country_id"))
                .countryName(resultSet.getString("country_name"))
                .build();
    }
    
    private ContactPerson mapContactPerson(ResultSet resultSet) throws SQLException {
        return ContactPerson.builder()
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .phoneNumber(resultSet.getString("phone_number"))
                .build();
    }
    
    private Currency mapCurrency(ResultSet resultSet) throws SQLException {
        return Currency.builder()
                .id(resultSet.getInt("currency_id"))
                .currencyType(resultSet.getString("currency_type"))
                .build();
    }
}