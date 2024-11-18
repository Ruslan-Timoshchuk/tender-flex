package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.dao.mapper.GrantedAuthorityRoleMapper;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;

@Repository
@RequiredArgsConstructor
public class GrantedAuthorityRoleRepositoryImpl implements GrantedAuthorityRoleRepository {

    public static final String GET_ROLES_BY_USER_QUERY = "SELECT id, role FROM roles "
            + "LEFT JOIN user_roles ur ON ur.role_id = id WHERE user_id = ?";
    public static final String GET_ROLE_BY_NAME = "SELECT id, role FROM roles WHERE role = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GrantedAuthorityRoleMapper roleMapper;

    @Override
    public List<GrantedAuthorityRole> getByUser(Integer userId) {
        return jdbcTemplate.query(GET_ROLES_BY_USER_QUERY, roleMapper, userId);
    }

    @Override
    public GrantedAuthorityRole getByName(ERole role) {
        return jdbcTemplate.queryForObject(GET_ROLE_BY_NAME, roleMapper, role.name());
    }
    
}