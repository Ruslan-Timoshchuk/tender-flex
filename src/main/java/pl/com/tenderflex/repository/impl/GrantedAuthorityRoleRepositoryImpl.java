package pl.com.tenderflex.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.GrantedAuthorityRole;
import pl.com.tenderflex.model.enums.ERole;
import pl.com.tenderflex.repository.GrantedAuthorityRoleRepository;
import pl.com.tenderflex.repository.mapper.GrantedAuthorityRoleMapper;

@Repository
@RequiredArgsConstructor
public class GrantedAuthorityRoleRepositoryImpl implements GrantedAuthorityRoleRepository {

    public static final String SELECT_BY_ID_QUERY = """
            SELECT id, role FROM roles LEFT JOIN user_roles ur ON ur.role_id = id
            WHERE user_id = ?""";
    public static final String SELECT_BY_NAME = "SELECT id, role FROM roles WHERE role = ?";

    private final JdbcTemplate jdbcTemplate;
    private final GrantedAuthorityRoleMapper roleMapper;

    @Override
    public List<GrantedAuthorityRole> findByUser(Integer userId) {
        return jdbcTemplate.query(SELECT_BY_ID_QUERY, roleMapper, userId);
    }

    @Override
    public GrantedAuthorityRole findByName(ERole role) {
        return jdbcTemplate.queryForObject(SELECT_BY_NAME, roleMapper, role.name());
    }

}