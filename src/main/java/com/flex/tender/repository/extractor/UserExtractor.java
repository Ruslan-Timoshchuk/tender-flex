package com.flex.tender.repository.extractor;

import static com.flex.tender.repository.sql.column.AuthorityColumns.*;
import static com.flex.tender.repository.sql.column.UserColumns.*;
import static java.util.Objects.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import com.flex.tender.model.Authority;
import com.flex.tender.model.User;
import com.flex.tender.repository.mapper.AuthorityMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserExtractor implements ResultSetExtractor<User> {

    private final AuthorityMapper authorityMapper;

    @Override
    public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        User user = null;
        List<Authority> authorities = new ArrayList<>();
        while (resultSet.next()) {
            if (isNull(user)) {
                user = User.builder()
                           .id(resultSet.getInt(USER_ID))
                           .firstName(resultSet.getString(USER_FIRST_NAME))
                           .lastName(resultSet.getString(USER_LAST_NAME))
                           .email(resultSet.getString(USER_EMAIL))
                           .password(resultSet.getString(USER_PASSWORD))
                           .authorities(authorities).build();
            }
            if (nonNull(resultSet.getObject(AUTHORITY_ID))) {
                authorities.add(authorityMapper.mapSingle(resultSet));
            }
        }
        return user;
    }

}