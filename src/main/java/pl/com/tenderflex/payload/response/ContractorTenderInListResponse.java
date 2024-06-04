package pl.com.tenderflex.payload.response;

import lombok.Data;

@Data
public class ContractorTenderInListResponse {

    private Integer tenderId;
    private String cpvCode;
    private String cpvDescription;
    private String officialName;
    private String status;
    private String deadline;
    private Integer offersAmount;

}