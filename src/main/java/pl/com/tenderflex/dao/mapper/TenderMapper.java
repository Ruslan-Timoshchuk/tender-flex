package pl.com.tenderflex.dao.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.impl.OfferRepositoryImpl;
import pl.com.tenderflex.dao.impl.UserRepositoryImpl;
import pl.com.tenderflex.exception.DataMappingException;
import pl.com.tenderflex.model.CPV;
import pl.com.tenderflex.model.CompanyDetails;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.ETenderStatus;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TypeOfTender;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class TenderMapper implements RowMapper<Tender> {

    private final UserRepositoryImpl userRepositoryImpl;
    private final OfferRepositoryImpl offerRepository;

    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        try {
            return Tender.builder()
                    .id(resultSet.getInt("id"))
                    .contractor(userRepositoryImpl.getById(resultSet.getInt("contractor_id")))
                    .contractorCompanyDetails(mapCompanyDetails(resultSet))
                    .contactPerson(mapContactPerson(resultSet))
                    .cpv(mapCPV(resultSet))
                    .type(mapTypeOfTender(resultSet))
                    .status(ETenderStatus.valueOf(resultSet.getString("status")))
                    .details(resultSet.getString("details"))
                    .minPrice(resultSet.getInt("min_price"))
                    .maxPrice(resultSet.getInt("max_price"))
                    .currency(mapCurrency(resultSet))
                    .publication(resultSet.getObject("publication_date", LocalDate.class))
                    .deadline(resultSet.getObject("deadline", LocalDate.class))
                    .deadlineForSignedContract(resultSet.getObject("deadline_for_signed_contract", LocalDate.class))
                    .contractFileName(resultSet.getString("contract_file_name"))
                    .awardDecisionFileName(resultSet.getString("award_decision_file_name"))
                    .rejectDecisionFileName(resultSet.getString("reject_decision_file_name"))
                    .offers(offerRepository.getByTender(resultSet.getInt("id")))
                    .build();
        } catch (SQLException e) {
            throw new DataMappingException("Error mapping row to Tender", e);
        }
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

    private CPV mapCPV(ResultSet resultSet) throws SQLException {
        return CPV.builder()
                .id(resultSet.getInt("cpv_id"))
                .code(resultSet.getString("code"))
                .description(resultSet.getString("description"))
                .build();
    }

    private TypeOfTender mapTypeOfTender(ResultSet resultSet) throws SQLException {
        return TypeOfTender.builder()
                .id(resultSet.getInt("type_of_tender_id"))
                .title(resultSet.getString("title"))
                .build();
    }

    private Currency mapCurrency(ResultSet resultSet) throws SQLException {
        return Currency.builder()
                .id(resultSet.getInt("currency_id"))
                .currencyType(resultSet.getString("currency_type"))
                .build();
    }
}