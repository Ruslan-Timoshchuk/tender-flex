package pl.com.tenderflex.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.ContractType;
import pl.com.tenderflex.repository.ContractTypeRepository;
import pl.com.tenderflex.repository.mapper.ContractTypeMapper;

@Repository
@RequiredArgsConstructor
public class ContractTypeRepositoryImpl implements ContractTypeRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractTypeRepositoryImpl.class);
    
    public static final String FIND_ALL_QUERY = "SELECT id AS contract_type_id, title AS contract_type_name FROM contract_types";

    private final JdbcTemplate jdbcTemplate;
    private final ContractTypeMapper contractTypeMapper;

    @Override
    public List<ContractType> getAll() {
        List<ContractType> contractTypes = jdbcTemplate.query(FIND_ALL_QUERY, contractTypeMapper);
        LOGGER.info("Successfully fetched {} contract-types", contractTypes.size());
        return contractTypes;
    }

}