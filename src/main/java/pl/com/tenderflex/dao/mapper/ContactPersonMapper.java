package pl.com.tenderflex.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import pl.com.tenderflex.model.ContactPerson;

@Component
public class ContactPersonMapper implements RowMapper<ContactPerson> {

    @Override
    public ContactPerson mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        ContactPerson contactPerson = new ContactPerson();
        contactPerson.setId(resultSet.getInt("id"));
        contactPerson.setFirstName(resultSet.getString("first_name"));
        contactPerson.setLastName(resultSet.getString("last_name"));
        contactPerson.setPhone(resultSet.getString("phone"));
        return contactPerson;
    }
}