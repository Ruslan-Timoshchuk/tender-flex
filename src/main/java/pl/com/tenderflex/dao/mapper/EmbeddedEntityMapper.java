package pl.com.tenderflex.dao.mapper;

import java.sql.SQLException;
import java.sql.ResultSet;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.CompanyDetails;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Procedure;
import pl.com.tenderflex.model.enums.ELanguage;
import pl.com.tenderflex.model.enums.EProcedure;

@Component
@RequiredArgsConstructor
class EmbeddedEntityMapper {

    private final CountryMapper countryMapper;
    
    protected CompanyDetails mapCompanyDetails(ResultSet resultSet, int rowNum) throws SQLException {
        return CompanyDetails.builder()
                 .officialName(resultSet.getString("official_name"))
                 .registrationNumber(resultSet.getString("registration_number"))
                 .country(countryMapper.mapRow(resultSet, rowNum))
                 .city(resultSet.getString("city"))
                 .build();
    }

    protected ContactPerson mapContactPerson(ResultSet resultSet) throws SQLException {
        return ContactPerson.builder()
                 .firstName(resultSet.getString("first_name"))
                 .lastName(resultSet.getString("last_name"))
                 .phoneNumber(resultSet.getString("phone_number"))
                 .build();
    }
    
    protected Procedure mapProcedure(ResultSet resultSet) throws SQLException {
        return Procedure
                 .builder()
                 .type(EProcedure.valueOf(resultSet.getString("procedure_type")))
                 .language(ELanguage.valueOf(resultSet.getString("language")))
                 .build();
    }
    
}