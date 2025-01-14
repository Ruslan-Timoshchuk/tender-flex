package pl.com.tenderflex.repository.impl;

import static java.lang.String.format;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Cpv;
import pl.com.tenderflex.repository.CpvRepository;
import pl.com.tenderflex.repository.mapper.CpvMapper;

@Repository
@RequiredArgsConstructor
public class CpvRepositoryImpl implements CpvRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CpvRepositoryImpl.class);
    
    public static final String FIND_ALL_PATTERN_QUERY = "SELECT %s, %s, %s FROM %s";
    public static final String CPV_ID = "id";
    public static final String CPV_CODE = "code";
    public static final String CPV_DESCRIPTION = "description";
    public static final String CPVS_TABLE = "cpvs";
   
    private final JdbcTemplate jdbcTemplate;
    private final CpvMapper cpvMapper;

    @Override
    public List<Cpv> findAll() {
        String sqlQuery = format(FIND_ALL_PATTERN_QUERY, CPV_ID, CPV_CODE, CPV_DESCRIPTION, CPVS_TABLE);
        LOGGER.debug("Executing SQL Query: {}", sqlQuery);
        List<Cpv> cpvs = jdbcTemplate.query(sqlQuery, cpvMapper);
        LOGGER.info("Successfully fetched {} cpvs", cpvs.size());
        return cpvs;
    }

}