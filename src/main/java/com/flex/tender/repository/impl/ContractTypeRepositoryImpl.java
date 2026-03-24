package com.flex.tender.repository.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.flex.tender.model.ContractType;
import com.flex.tender.repository.ContractTypeRepository;
import com.flex.tender.repository.mapper.ContractTypeMapper;

import lombok.RequiredArgsConstructor;

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