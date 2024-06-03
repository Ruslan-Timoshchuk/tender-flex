package pl.com.tenderflex.dao.impl;

import static java.util.stream.Collectors.toSet;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.dao.mapper.GrantedAuthorityRoleMapper;
import pl.com.tenderflex.model.GrantedAuthorityRole;

@Repository
@RequiredArgsConstructor
public class GrantedAuthorityRoleRepositoryImpl implements GrantedAuthorityRoleRepository {

    public static final String GET_ROLES_BY_USER_QUERY = "SELECT id, role FROM roles "
            + "LEFT JOIN user_roles ur ON ur.role_id = id WHERE user_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GrantedAuthorityRoleMapper roleMapper;

    @Override
    public Set<GrantedAuthorityRole> getByUser(Integer userId) {
        return jdbcTemplate.query(GET_ROLES_BY_USER_QUERY, roleMapper, userId).stream().collect(toSet());
        }
}