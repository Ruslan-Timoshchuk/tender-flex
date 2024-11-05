package pl.com.tenderflex.dao.impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContractTypeRepository;
import pl.com.tenderflex.dao.mapper.ContractTypeMapper;
import pl.com.tenderflex.model.ContractType;

@Repository
@RequiredArgsConstructor
public class ContractTypeRepositoryImpl implements ContractTypeRepository {

    public static final String GET_ALL_CONTRACT_TYPES_QUERY = "SELECT id, title FROM contract_types";
    
    private final JdbcTemplate jdbcTemplate;
    private final ContractTypeMapper contractTypeMapper;
    
    @Override
    public List<ContractType> getAll() {
        return jdbcTemplate.query(GET_ALL_CONTRACT_TYPES_QUERY, contractTypeMapper);
    }
    
}