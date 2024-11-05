package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class ContractRequest {

    private Integer tenderId;
    private Integer contractTypeId;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer currencyId;
    private Integer contractFileId;
    private String signedDeadline;
    
}