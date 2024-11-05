package pl.com.tenderflex.dao.impl;

import java.sql.PreparedStatement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.dao.ContractRepository;
import pl.com.tenderflex.model.Contract;

@Repository
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository {

    public static final String ADD_NEW_CONTRACT_QUERY = "INSERT INTO contracts("
            + "tender_id, contract_type_id, min_price, max_price, currency_id, contract_file_id, signed_deadline) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private final JdbcTemplate jdbcTemplate;
    
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
                statement.setInt(6, contract.getContractFile().getId());
                statement.setObject(7, contract.getSignedDeadline());
                return statement;
            }, keyHolder);
            contract.setId(keyHolder.getKeyAs(Integer.class));
            return contract;
        }
    
    }