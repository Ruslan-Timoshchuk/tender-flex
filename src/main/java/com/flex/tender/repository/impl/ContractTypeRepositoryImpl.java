package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.ContractType;
import com.flex.tender.repository.ContractTypeRepository;
import com.flex.tender.repository.mapper.ContractTypeMapper;
import com.flex.tender.repository.sql.mixins.ContractTypeMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContractTypeRepositoryImpl implements ContractTypeRepository {

    public static final String FIND_ALL_QUERY = """
            SELECT %s 
            FROM contract_types contract_type
            """.formatted(
                ContractTypeMixins.CONTRACT_TYPE_QUERY_COLUMNS);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s 
            FROM contract_types contract_type 
            WHERE id = :id""";

    private final NamedParameterJdbcTemplate jdbc;
    private final ContractTypeMapper contractTypeMapper;

    @Override
    public List<ContractType> findAll() {
        return jdbc.query(FIND_ALL_QUERY, contractTypeMapper);
    }

    @Override
    public ContractType findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, contractTypeMapper);
    }

}