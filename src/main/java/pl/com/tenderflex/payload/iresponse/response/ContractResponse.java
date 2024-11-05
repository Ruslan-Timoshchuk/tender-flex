package pl.com.tenderflex.payload.iresponse.response;

import lombok.Data;

@Data
public class ContractResponse {

    private Integer id;
    private ContractTypeResponse contractType;
    private Integer minPrice;
    private Integer maxPrice;
    private CurrencyResponse currency;
    private FileMetadataResponse fileMetadata;
    private String signedDeadline;
    
}