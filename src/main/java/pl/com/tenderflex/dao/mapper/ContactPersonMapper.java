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
        return ContactPerson
                .builder()
                .id(resultSet.getInt("id"))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .phone(resultSet.getString("phone"))
                .build();
    }
}