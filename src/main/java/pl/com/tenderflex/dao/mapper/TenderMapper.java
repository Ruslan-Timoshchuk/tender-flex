package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Currency;
import pl.com.tenderflex.model.Organization;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.TenderType;

@Component
public class TenderMapper implements RowMapper<Tender> {
   
    @Override
    public Tender mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Tender
                .builder()
                .id(resultSet.getInt("id"))
                .contractorId(resultSet.getInt("contractor_id"))
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
                .cpvCode(resultSet.getString("cpv_code"))
                .type(TenderType.valueOf(resultSet.getString("tender_type")))
                .details(resultSet.getString("details"))
                .minPrice(resultSet.getInt("min_price"))
                .maxPrice(resultSet.getInt("max_price"))
                .currency(Currency
                        .builder()
                        .id(resultSet.getInt("currency_id"))
                        .currencyType(resultSet.getString("currency_type"))
                        .build())
                .publication(resultSet.getObject("publication_date", LocalDate.class))
                .deadline(resultSet.getObject("deadline", LocalDate.class))
                .deadlineForSignedContract(resultSet.getObject("deadline_for_signed_contract", LocalDate.class))
                .contractFileName(resultSet.getString("contract_file_name"))
                .awardDecisionFileName(resultSet.getString("award_decision_file_name"))
                .rejectDecisionFileName(resultSet.getString("reject_decision_file_name"))
                .build();
    }
}