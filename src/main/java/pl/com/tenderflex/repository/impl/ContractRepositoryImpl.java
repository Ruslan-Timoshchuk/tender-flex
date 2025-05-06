package pl.com.tenderflex.repository.impl;

import java.sql.PreparedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.repository.ContractRepository;
import pl.com.tenderflex.repository.mapper.ContractMapper;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractRepositoryImpl.class);

    public static final String EXECUTING_SQL_QUERY_LOG = "Executing SQL Query: {}";
    public static final String ADD_NEW_CONTRACT_QUERY = """
            INSERT INTO contracts(tender_id, contract_type_id, min_price, max_price, currency_id, file_id, has_signed, signed_deadline)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)""";
    public static final String UPDATE_CONTRACT_QUERY = """
            UPDATE contracts
            SET offer_id = ?, contract_type_id = ?, min_price = ?, max_price = ?, currency_id = ?, file_id = ?, signed_deadline = ?, signed_date = ?
            WHERE id = ?
            """;
    public static final String SELECT_BY_ID_QUERY = """
            SELECT contract.id AS contract_id, contract.tender_id, contract.offer_id, contract.contract_type_id,
            contract_type.title AS contract_type_name, contract.min_price, contract.max_price,
            contract.currency_id, currency.code, currency.symbol, contract_file.id AS contract_file_id,
            contract_file.name AS contract_file_name, contract_file.content_type AS contract_file_content_type,
            contract_file.aws_s3_file_key AS contract_aws_s3_file_key, contract.signed_deadline, contract.signed_date
            FROM contracts contract
            LEFT JOIN contract_types contract_type ON contract_type.id = contract.contract_type_id
            LEFT JOIN currencies currency ON currency.id = contract.currency_id
            LEFT JOIN files contract_file ON contract_file.id = contract.file_id
            WHERE contract.id = ?""";

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
            statement.setBoolean(7, contract.isHasSigned());
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
                contract.getFileMetadata().getId(), contract.getSignedDeadline(), contract.getSignedDate(),
                contract.getId());
    }

    @Override
    public Contract findById(Integer id) {
        LOGGER.debug(EXECUTING_SQL_QUERY_LOG, SELECT_BY_ID_QUERY);
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, contractMapper, id);
    }

}