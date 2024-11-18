package pl.com.tenderflex.dao.impl;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.CpvRepository;
import pl.com.tenderflex.dao.mapper.CpvMapper;
import pl.com.tenderflex.model.Cpv;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    public static final String GET_ALL_CPVS_QUERY = "SELECT id AS cpv_id, code, description FROM cpvs";

    private final JdbcTemplate jdbcTemplate;
    private final CpvMapper cpvMapper;

    @Override
    public Set<Cpv> getAllCpvs() {
        return jdbcTemplate.query(GET_ALL_CPVS_QUERY, cpvMapper).stream().collect(Collectors.toSet());
    }

}