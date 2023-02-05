package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.Country;
import pl.com.tenderflex.model.Organization;

@Component
public class OrganizationMapper implements RowMapper<Organization> {

    private final ContactPersonMapper contactPersonMapper;

    public OrganizationMapper(ContactPersonMapper contactPersonMapper) {
        this.contactPersonMapper = contactPersonMapper;
    }

    @Override
    public Organization mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Organization organization = new Organization(contactPersonMapper.mapRow(resultSet, rowNum));
        organization.setId(resultSet.getInt("id"));
        organization.setName(resultSet.getString("organization_name"));
        organization.setNationalRegistrationNumber(resultSet.getString("national_registration_number"));
        organization.setCountry(Country.valueOf(resultSet.getString("country")));
        organization.setCity(resultSet.getString("city"));
        return organization;
    }
}