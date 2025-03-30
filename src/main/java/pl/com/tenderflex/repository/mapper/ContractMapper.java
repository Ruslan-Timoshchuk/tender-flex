package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;

@Component
@RequiredArgsConstructor
public class ContractMapper {
    
    public static final String CONTRACT_ID = "contract_id";
    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";  
    public static final String CONTRACT_FILE_ID = "contract_file_id";
    public static final String CONTRACT_FILE_NAME = "contract_file_name";
    public static final String CONTRACT_FILE_CONTENT_TYPE = "contract_file_content_type";
    public static final String CONTRACT_FILE_AWS3_KEY = "contract_aws_s3_file_key"; 
    public static final String SIGNED_DEADLINE = "signed_deadline";
    public static final String SIGNED_DATE = "signed_date";
    
    private final ContractTypeMapper contractTypeMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
     
    public Contract mapContract(ResultSet resultSet) throws SQLException {
        return Contract
                .builder()
                .id(resultSet.getInt(CONTRACT_ID))
                .contractType(contractTypeMapper.mapContractType(resultSet))
                .minPrice(resultSet.getInt(MIN_PRICE))
                .maxPrice(resultSet.getInt(MAX_PRICE))
                .currency(currencyMapper.mapCurrency(resultSet))
                .fileMetadata(fileMeatadataMapper.
                        mapFileMetadata(resultSet, 
                                Map.of(FILE_ID, CONTRACT_FILE_ID,
                                       FILE_NAME, CONTRACT_FILE_NAME,
                                       FILE_CONTENT_TYPE, CONTRACT_FILE_CONTENT_TYPE,
                                       FILE_AWS3_KEY, CONTRACT_FILE_AWS3_KEY)))
                .signedDeadline(resultSet.getObject(SIGNED_DEADLINE, LocalDate.class))
                .signedDate(resultSet.getObject(SIGNED_DATE, LocalDate.class))
                .build();
    }
    
}