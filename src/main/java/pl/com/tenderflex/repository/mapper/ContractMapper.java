package pl.com.tenderflex.repository.mapper;

import static pl.com.tenderflex.repository.mapper.FileMeatadataMapper.*;
import static pl.com.tenderflex.repository.mapper.TenderMapper.TENDER_ID;
import static  pl.com.tenderflex.repository.mapper.OfferMapper.OFFER_ID;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import pl.com.tenderflex.model.Contract;
import pl.com.tenderflex.model.Offer;
import pl.com.tenderflex.model.Tender;
import pl.com.tenderflex.model.enums.EContractStatus;

@Component
@RequiredArgsConstructor
public class ContractMapper implements RowMapper<Contract> {
    
    public static final String CONTRACT_ID = "contract_id";
    public static final String MIN_PRICE = "min_price";
    public static final String MAX_PRICE = "max_price";  
    public static final String CONTRACT_FILE_ID = "contract_file_id";
    public static final String CONTRACT_FILE_NAME = "contract_file_name";
    public static final String CONTRACT_FILE_CONTENT_TYPE = "contract_file_content_type";
    public static final String CONTRACT_FILE_AWS3_KEY = "contract_aws_s3_file_key"; 
    public static final String GLOBAL_STATUS = "global_status";
    public static final String SIGNED_DEADLINE = "signed_deadline";
    public static final String SIGNED_DATE = "signed_date";
    
    private final ContractTypeMapper contractTypeMapper;
    private final CurrencyMapper currencyMapper;
    private final FileMeatadataMapper fileMeatadataMapper;
     
    @Override
    public Contract mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Contract
                .builder()
                .id(resultSet.getInt(CONTRACT_ID))
                .tender(Tender
                          .builder()
                          .id(resultSet.getObject(TENDER_ID, Integer.class))
                          .build())
                .offer(Offer
                        .builder()
                        .id(resultSet.getObject(OFFER_ID, Integer.class))
                        .build())
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
                .globalStatus(EContractStatus.valueOf(resultSet.getString(GLOBAL_STATUS)))
                .signedDeadline(resultSet.getObject(SIGNED_DEADLINE, LocalDate.class))
                .signedDate(resultSet.getObject(SIGNED_DATE, LocalDate.class))
                .build();
    }
    
}