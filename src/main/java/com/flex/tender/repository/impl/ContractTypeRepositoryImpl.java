package com.flex.tender.repository.impl;

import static com.flex.tender.repository.sql.mixins.ContractTypeMixins.*;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.ContractType;
import com.flex.tender.repository.ContractTypeRepository;
import com.flex.tender.repository.mapper.ContractTypeMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContractTypeRepositoryImpl implements ContractTypeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ContractTypeMapper contractTypeMapper;

    @Override
    public List<ContractType> findAll() {
        return jdbcTemplate.query(FIND_ALL_QUERY, contractTypeMapper);
    }

    @Override
    public ContractType findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, contractTypeMapper, id);
    }

}