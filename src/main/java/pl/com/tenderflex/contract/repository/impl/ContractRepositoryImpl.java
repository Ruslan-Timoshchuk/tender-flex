package pl.com.tenderflex.contract.repository.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.contract.model.Contract;
import pl.com.tenderflex.contract.repository.ContractRepository;
import pl.com.tenderflex.contract.repository.mapper.ContractMapper;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    public static final String ADD_NEW_CONTRACT_QUERY = "INSERT INTO contracts("
            + "tender_id, contract_type_id, min_price, max_price, currency_id, file_id, signed_deadline) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String FIND_CONTRACT_BY_TENDER_ID_QUERY = "SELECT cs.id, cs.contract_type_id, ct.title, cs.min_price, "
            + "cs.max_price, cs.currency_id, cur.currency_type, cs.file_id, cs.signed_deadline, cs.signed_date, "
            + "cs.file_id, fs.name, fs.content_type, fs.aws_s3_file_key "
            + "FROM contracts cs "
            + "LEFT JOIN contract_types ct ON ct.id = cs.contract_type_id "
            + "LEFT JOIN currencies cur ON cur.id = cs.currency_id "
            + "LEFT JOIN files fs ON fs.id = cs.file_id "
            + "WHERE cs.tender_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final ContractMapper contractMapper;

    @Override
    public Contract save(Contract contract) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(ADD_NEW_CONTRACT_QUERY, new String[] { "id" });
            statement.setInt(1, contract.getTenderId());
            statement.setInt(2, contract.getContractType().getId());
            statement.setInt(3, contract.getMinPrice());
            statement.setInt(4, contract.getMaxPrice());
            statement.setInt(5, contract.getCurrency().getId());
            statement.setInt(6, contract.getFileMetadata().getId());
            statement.setObject(7, contract.getSignedDeadline());
            return statement;
        }, keyHolder);
        contract.setId(keyHolder.getKeyAs(Integer.class));
        return contract;
    }

    @Override
    public Contract findByTenderId(Integer id) {
        return jdbcTemplate.queryForObject(FIND_CONTRACT_BY_TENDER_ID_QUERY, contractMapper, id);
    }

}