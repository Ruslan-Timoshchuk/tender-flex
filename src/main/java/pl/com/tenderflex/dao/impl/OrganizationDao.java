package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.ContactPerson;
import pl.com.tenderflex.model.Organization;

@Repository
@RequiredArgsConstructor
public class OrganizationDao {

    public static final String ADD_NEW_ORGANIZATION_QUERY = "INSERT INTO "
            + "organizations(organization_name, national_registration_number, country_id, city, contact_person_id) VALUES (?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;
    private final ContactPersonDao contactPersonDao;

    public Organization create(Organization organization) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        ContactPerson contactPerson = contactPersonDao.create(organization.getContactPerson());
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_ORGANIZATION_QUERY,
                    new String[] { "id" });
            statement.setString(1, organization.getName());
            statement.setString(2, organization.getNationalRegistrationNumber());
            statement.setInt(3, organization.getCountry().getId());
            statement.setString(4, organization.getCity());
            statement.setInt(5, contactPerson.getId());
            return statement;
        }, keyHolder);
        organization.setId(keyHolder.getKeyAs(Integer.class));
        return organization;
    }
}