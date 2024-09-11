package pl.com.tenderflex.payload.request;

import lombok.Data;

@Data
public class ContractDetailsRequest {

    private String offerSubmissionDeadline;
    private String signedContractDeadline;
    
}