package com.flex.tender.repository.sql.query;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ContractQueries {

    public final String ADD_NEW_CONTRACT_QUERY = """
            INSERT INTO contracts(award_decision_id, contract_type_id, min_price, max_price, currency_id, file_id, global_status, signed_deadline)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    public final String UPDATE_CONTRACT_QUERY = """
            UPDATE contracts
            SET contract_type_id = ?, min_price = ?, max_price = ?, currency_id = ?,
            file_id = ?, global_status = ?, signed_deadline = ?, signed_date = ?
            WHERE id = ?
            """;
    public final String SELECT_BY_ID_PATTERN_QUERY = "SELECT %s FROM contracts contract %s WHERE contract.id = ?";
    public final String FIND_BY_AWARD_DECISION_ID_PATTERN_QUERY = "SELECT %s FROM contracts contract %s WHERE contract.award_decision_id = ?";
    public final String SELECT_ALL_BY_IS_SIGNED_PATTERN_QUERY = "SELECT %s FROM contracts contract %s WHERE contract.global_status = ?";
    public final String CONTRACT_COLUMNS_SQL_PART_QUERY = """
            contract.id AS contract_id, contract.tender_id, contract.offer_id, contract.contract_type_id,
            contract_type.title AS contract_type_name, contract.min_price, contract.max_price,
            contract.currency_id, currency.code, currency.symbol, contract_file.id AS contract_file_id,
            contract_file.name AS contract_file_name, contract_file.content_type AS contract_file_content_type,
            contract_file.aws_s3_file_key AS contract_aws_s3_file_key, contract.global_status, contract.signed_deadline, contract.signed_date
            """;
    public final String CONTRACT_JOIN_TABLES_SQL_PART_QUERY = """
            LEFT JOIN contract_types contract_type ON contract_type.id = contract.contract_type_id
            LEFT JOIN currencies currency ON currency.id = contract.currency_id
            LEFT JOIN files contract_file ON contract_file.id = contract.file_id
            """;
    
}