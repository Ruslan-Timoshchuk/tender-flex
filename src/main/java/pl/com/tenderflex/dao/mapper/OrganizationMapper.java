package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CountryRepository;
import pl.com.tenderflex.dao.impl.ContactPersonDao;
import pl.com.tenderflex.model.Organization;

@Component
@RequiredArgsConstructor
public class OrganizationMapper implements RowMapper<Organization> {

    private final ContactPersonDao contactPersonDao;
    private final CountryRepository countryRepository;

    @Override
    public Organization mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Organization
                .builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("organization_name"))
                .nationalRegistrationNumber(resultSet.getString("national_registration_number"))
                .country(countryRepository.getById(resultSet.getInt("country_id")))
                .city(resultSet.getString("city"))
                .contactPerson(contactPersonDao.getById(resultSet.getInt("contact_person_id"))).build();
    }
}