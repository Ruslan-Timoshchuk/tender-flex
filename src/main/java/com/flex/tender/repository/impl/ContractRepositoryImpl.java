package com.flex.tender.repository.impl;

import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.flex.tender.model.Contract;
import com.flex.tender.model.enumeration.EContractStatus;
import com.flex.tender.repository.ContractRepository;
import com.flex.tender.repository.mapper.ContractMapper;
import com.flex.tender.repository.sql.query.ContractMixins;
import com.flex.tender.repository.sql.query.ContractTypeMixins;
import com.flex.tender.repository.sql.query.CurrencyMixins;
import com.flex.tender.repository.sql.query.FileMetadataMixins;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    public static final String INSERT_QUERY = """
            INSERT INTO contracts(%s) 
            VALUES (%s)
            """.formatted(
                ContractMixins.CONTRACT_INSERT_COLUMNS,
                ContractMixins.CONTRACT_INSERT_VALUE_PARAMETERS);
    public static final String UPDATE_QUERY = """
            UPDATE contracts
            SET %s 
            WHERE id = :id
            """.formatted(
                ContractMixins.CONTRACT_UPDATE_SET_CLAUSE);
    public static final String FIND_BY_ID_QUERY = """
            SELECT %s, %s, %s, %s 
            FROM contracts contract 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            WHERE contract.id = :id
            """.formatted(
                ContractMixins.CONTRACT_QUERY_COLUMNS,
                ContractTypeMixins.CONTRACT_TYPE_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                ContractTypeMixins.CONTRACT_TYPE_JOIN_CONTRACTS,
                CurrencyMixins.CURRENCY_JOIN_CONTRACTS,
                FileMetadataMixins.FILE_METADATA_JOIN_CONTRACTS);
    public static final String FIND_ALL_BY_STATUS_QUERY = """
            SELECT %s, %s, %s, %s 
            FROM contracts contract 
            LEFT JOIN %s 
            LEFT JOIN %s 
            LEFT JOIN %s 
            WHERE contract.global_status = :globalStatus
            """.formatted( 
                ContractMixins.CONTRACT_QUERY_COLUMNS,
                ContractTypeMixins.CONTRACT_TYPE_QUERY_COLUMNS,
                CurrencyMixins.CURRENCY_QUERY_COLUMNS,
                FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
                ContractTypeMixins.CONTRACT_TYPE_JOIN_CONTRACTS,
                CurrencyMixins.CURRENCY_JOIN_CONTRACTS,
                FileMetadataMixins.FILE_METADATA_JOIN_CONTRACTS);
    public static final String FIND_BY_AWARD_DECISION_ID_QUERY = """
           SELECT %s, %s, %s, %s 
           FROM contracts contract 
           LEFT JOIN %s 
           LEFT JOIN %s 
           LEFT JOIN %s  
           WHERE contract.award_decision_id = :awardDecisionId
           """.formatted(
               ContractMixins.CONTRACT_QUERY_COLUMNS,
               ContractTypeMixins.CONTRACT_TYPE_QUERY_COLUMNS,
               CurrencyMixins.CURRENCY_QUERY_COLUMNS,
               FileMetadataMixins.FILE_METADATA_QUERY_COLUMNS,
               ContractTypeMixins.CONTRACT_TYPE_JOIN_CONTRACTS,
               CurrencyMixins.CURRENCY_JOIN_CONTRACTS,
               FileMetadataMixins.FILE_METADATA_JOIN_CONTRACTS);
    
    private final NamedParameterJdbcTemplate jdbc;
    private final ContractMapper contractMapper;

    @Override
    public Contract save(Contract contract) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("awardDecisionId", contract.getAwardDecision().getId())
                .addValue("contractTypeId", contract.getContractType().getId())
                .addValue("minPrice", contract.getMinPrice())
                .addValue("maxPrice", contract.getMaxPrice())
                .addValue("currencyId", contract.getCurrency().getId())
                .addValue("fileMetadataId", contract.getFileMetadata().getId())
                .addValue("globalStatus", contract.getGlobalStatus().name())
                .addValue("signedDeadline", contract.getSignedDeadline());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(
                INSERT_QUERY, 
                parameters,
                keyHolder, 
                new String[] { "id" });
        contract.setId(keyHolder.getKeyAs(Integer.class));
        return contract;
    }

    @Override
    public void update(Contract contract) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", contract.getId())
                .addValue("contractTypeId", contract.getContractType().getId())
                .addValue("minPrice", contract.getMinPrice())
                .addValue("maxPrice", contract.getMaxPrice())
                .addValue("currencyId", contract.getCurrency().getId())
                .addValue("fileMetadataId", contract.getFileMetadata().getId())
                .addValue("globalStatus", contract.getGlobalStatus().name())
                .addValue("signedDeadline", contract.getSignedDeadline())
                .addValue("signedDate", contract.getSignedDate());
        jdbc.update(UPDATE_QUERY, parameters);
    }

    @Override
    public Contract findById(Integer id) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbc.queryForObject(FIND_BY_ID_QUERY, parameters, contractMapper);
    }

    @Override
    public List<Contract> findAll(EContractStatus globalStatus) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("globalStatus", globalStatus.name());
        return jdbc.query(FIND_ALL_BY_STATUS_QUERY, parameters, contractMapper);
    }

    @Override
    public Contract findByAwardDecisionId(Integer awardDecisionId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("awardDecisionId", awardDecisionId);
        return jdbc.queryForObject(FIND_BY_AWARD_DECISION_ID_QUERY, parameters, contractMapper);
    }

}