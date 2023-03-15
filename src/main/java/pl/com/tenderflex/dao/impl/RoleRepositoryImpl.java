package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.RoleRepository;
import pl.com.tenderflex.dao.mapper.RoleMapper;
import pl.com.tenderflex.model.Role;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    public static final String GET_ROLES_BY_USER_QUERY = "SELECT id, name FROM roles "
            + "LEFT JOIN user_roles ur ON ur.role_id = id WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RoleMapper roleMapper;

    @Override
    public List<Role> getByUser(Integer userId) {
        return jdbcTemplate.query(GET_ROLES_BY_USER_QUERY, roleMapper, userId);
    }
}