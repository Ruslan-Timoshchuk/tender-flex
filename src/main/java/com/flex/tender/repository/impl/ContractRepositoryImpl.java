package com.flex.tender.repository.impl;

import static java.util.stream.Collectors.toSet;
import static com.flex.tender.repository.sql.query.ContractQueries.*;
import java.sql.PreparedStatement;
import java.util.Set;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.repository.mapper.ContractMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ContractMapper contractMapper;

    @Override
    public Contract save(Contract contract) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_CONTRACT_QUERY, new String[] { "id" });
            statement.setInt(1, contract.getTender().getId());
            statement.setInt(2, contract.getContractType().getId());
            statement.setInt(3, contract.getMinPrice());
            statement.setInt(4, contract.getMaxPrice());
            statement.setInt(5, contract.getCurrency().getId());
            statement.setInt(6, contract.getFileMetadata().getId());
            statement.setString(7, contract.getGlobalStatus().name());
            statement.setObject(8, contract.getSignedDeadline());
            return statement;
        }, keyHolder);
        contract.setId(keyHolder.getKeyAs(Integer.class));
        return contract;
    }

    @Override
    public void update(Contract contract) {
        jdbcTemplate.update(UPDATE_CONTRACT_QUERY, contract.getOffer().getId(), contract.getContractType().getId(),
                contract.getMinPrice(), contract.getMaxPrice(), contract.getCurrency().getId(),
                contract.getFileMetadata().getId(), contract.getGlobalStatus().name(), contract.getSignedDeadline(),
                contract.getSignedDate(), contract.getId());
    }

    @Override
    public Contract findById(Integer id) {
        String sqlQuery = String.format(SELECT_BY_ID_PATTERN_QUERY, CONTRACT_COLUMNS_SQL_PART_QUERY,
                CONTRACT_JOIN_TABLES_SQL_PART_QUERY);
        return jdbcTemplate.queryForObject(sqlQuery, contractMapper, id);
    }

    @Override
    public Set<Contract> findAll(EContractStatus globalStatus) {
        String sqlQuery = String.format(SELECT_ALL_BY_IS_SIGNED_PATTERN_QUERY, CONTRACT_COLUMNS_SQL_PART_QUERY,
                CONTRACT_JOIN_TABLES_SQL_PART_QUERY);
        return jdbcTemplate.query(sqlQuery, contractMapper, globalStatus.name()).stream().collect(toSet());
    }

    @Override
    public Contract findByTenderId(Integer id) {
        String sqlQuery = String.format(FIND_BY_TENDER_ID_PATTERN_QUERY, CONTRACT_COLUMNS_SQL_PART_QUERY,
                CONTRACT_JOIN_TABLES_SQL_PART_QUERY);
        return jdbcTemplate.queryForObject(sqlQuery, contractMapper, id);
    }

}