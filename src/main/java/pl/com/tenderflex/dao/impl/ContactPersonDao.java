package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.mapper.ContactPersonMapper;
import pl.com.tenderflex.model.ContactPerson;

@Repository
@RequiredArgsConstructor
public class ContactPersonDao {

    public static final String ADD_NEW_CONTACT_PERSON_QUERY = "INSERT INTO "
            + "contact_persons(first_name, last_name, phone) VALUES (?, ?, ?)";
    public static final String GET_CONTACT_PERSON_BY_ID_QUERY = "SELECT * FROM contact_persons WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final ContactPersonMapper contactPersonMapper;

    public ContactPerson create(ContactPerson contactPerson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_CONTACT_PERSON_QUERY,
                    new String[] { "id" });
            statement.setString(1, contactPerson.getFirstName());
            statement.setString(2, contactPerson.getLastName());
            statement.setString(3, contactPerson.getPhone());
            return statement;
        }, keyHolder);
        contactPerson.setId(keyHolder.getKeyAs(Integer.class));
        return contactPerson;
    }
    
    public ContactPerson getById(Integer contactPersonId) {
        return jdbcTemplate.queryForObject(GET_CONTACT_PERSON_BY_ID_QUERY, contactPersonMapper, contactPersonId);
    }
}