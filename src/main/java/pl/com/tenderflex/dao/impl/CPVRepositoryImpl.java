package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CPVrepository;
import pl.com.tenderflex.dao.mapper.CPVmapper;
import pl.com.tenderflex.model.CPV;

@Repository
@RequiredArgsConstructor
public class CPVRepositoryImpl implements CPVrepository {

    public static final String GET_ALL_CPVS_QUERY = "SELECT id, code, description FROM cpvs";

    private final JdbcTemplate jdbcTemplate;
    private final CPVmapper cpvMapper;

    @Override
    public List<CPV> getAllCPVs() {
        return jdbcTemplate.query(GET_ALL_CPVS_QUERY, cpvMapper);
    }
}